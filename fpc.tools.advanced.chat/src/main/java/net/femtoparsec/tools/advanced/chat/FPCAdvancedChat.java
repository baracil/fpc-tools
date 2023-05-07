package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.*;
import fpc.tools.advanced.chat.event.AdvancedChatEvent;
import fpc.tools.advanced.chat.event.ReceivedMessage;
import fpc.tools.chat.Chat;
import fpc.tools.chat.event.Error;
import fpc.tools.chat.event.*;
import fpc.tools.lang.Instants;
import fpc.tools.lang.Listeners;
import fpc.tools.lang.Looper;
import fpc.tools.lang.Subscription;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Synchronized;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FPCAdvancedChat<M> implements AdvancedChat<M> {

    private final Chat chat;

    private final RequestAnswerMatcher<M> matcher;

    private final MessageConverter<M> messageConverter;


    private final Listeners<AdvancedChatListener<M>> listeners = Listeners.create();

    private Subscription subscription = Subscription.NONE;

    @Getter
    @Setter
    private Duration timeout = Duration.ofSeconds(30);

    private final EventHandler eventHandler = new EventHandler();

    private final ChatSender<M> chatSender;
    private final ChatReceiver<M> chatReceiver;

    private final Looper senderLooper;
    private final Looper receiverLooper;

    public FPCAdvancedChat(
            Chat chat,
            RequestAnswerMatcher<M> matcher,
            MessageConverter<M> messageConverter,
            Instants instants) {
        this.chat = chat;
        this.messageConverter = messageConverter;
        this.matcher = matcher;
        final BlockingDeque<RequestPostData<?, M>> postDataQueue = new LinkedBlockingDeque<>();
        this.chatSender = new ChatSender<>(chat, listeners, postDataQueue, instants);
        this.chatReceiver = new ChatReceiver<>(matcher::shouldPerformMatching, postDataQueue);

        this.senderLooper = Looper.simple(chatSender);
        this.receiverLooper = Looper.simple(chatReceiver);
    }

    @Override
    @Synchronized
    public void connect() {
        subscription.unsubscribe();
        subscription = chat.addChatListener(this::onChatEvent);
        chat.connect();
        senderLooper.startAndWaitForStart();
        receiverLooper.startAndWaitForStart();
    }

    @Override
    @Synchronized
    public boolean isRunning() {
        return chat.isRunning();
    }

    @Override
    @Synchronized
    public void requestDisconnection() {
        subscription.unsubscribe();
        subscription = Subscription.NONE;
        senderLooper.requestStop();
        receiverLooper.requestStop();
        chat.requestDisconnection();
    }


    private void onChatEvent(ChatEvent chatEvent) {
        chatEvent.accept(eventHandler);
    }

    @Override
    public Subscription addChatListener(AdvancedChatListener<M> listener) {
        return listeners.addListener(listener);
    }


    @Override
    public CompletionStage<DispatchSlip> sendCommand(Command command) {
        return chatSender.send(new CommandPostData<>(command));
    }

    @Override
    public <A> CompletionStage<ReceiptSlip<A>> sendRequest(Request<A> request) {
        return chatSender.send(new RequestPostData<>(request, matcher));
    }

    private void warnListeners(AdvancedChatEvent<M> event) {
        listeners.forEachListeners(AdvancedChatListener::onChatEvent, event);
    }


    private class EventHandler implements ChatEventVisitor {

        @Override
        public void visit(Connection event) {
            warnListeners(fpc.tools.advanced.chat.event.Connection.create());
        }

        @Override
        public void visit(Disconnection event) {
            warnListeners(fpc.tools.advanced.chat.event.Disconnection.create());
        }

        @Override
        public void visit(Error event) {
            warnListeners(fpc.tools.advanced.chat.event.Error.create(event.error()));
        }

        @Override
        public void visit(PostedMessage event) {
        }

        @Override
        public void visit(fpc.tools.chat.event.ReceivedMessage event) {
            final Instant receptionTime = event.getReceptionTime();
            Stream.of(event.message().split("\\R"))
                  .map(messageConverter::convert)
                  .flatMap(Optional::stream)
                  .map(m -> new ReceivedMessage<>(receptionTime, m))
                  .forEach(this::dispatchReceivedMessage);
        }

        private void dispatchReceivedMessage(ReceivedMessage<M> receivedMessage) {
            listeners.forEachListeners(AdvancedChatListener::onChatEvent, receivedMessage);
            chatReceiver.onMessageReception(receivedMessage);
        }


    }
}
