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
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class FPCAdvancedChat<M> implements AdvancedChat<M> {

    @NonNull
    private final Chat chat;

    @NonNull
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

    private final @NonNull Looper senderLooper;
    private final @NonNull Looper receiverLooper;

    public FPCAdvancedChat(
            @NonNull Chat chat,
            @NonNull RequestAnswerMatcher<M> matcher,
            @NonNull MessageConverter<M> messageConverter,
            @NonNull Instants instants) {
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


    private void onChatEvent(@NonNull ChatEvent chatEvent) {
        chatEvent.accept(eventHandler);
    }

    @Override
    public @NonNull Subscription addChatListener(@NonNull AdvancedChatListener<M> listener) {
        return listeners.addListener(listener);
    }


    @Override
    public @NonNull CompletionStage<DispatchSlip> sendCommand(@NonNull Command command) {
        return chatSender.send(new CommandPostData<>(command));
    }

    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A>> sendRequest(@NonNull Request<A> request) {
        return chatSender.send(new RequestPostData<>(request, matcher));
    }

    private void warnListeners(@NonNull AdvancedChatEvent<M> event) {
        listeners.forEachListeners(AdvancedChatListener::onChatEvent, event);
    }


    private class EventHandler implements ChatEventVisitor {

        @Override
        public void visit(@NonNull Connection event) {
            warnListeners(fpc.tools.advanced.chat.event.Connection.create());
        }

        @Override
        public void visit(@NonNull Disconnection event) {
            warnListeners(fpc.tools.advanced.chat.event.Disconnection.create());
        }

        @Override
        public void visit(@NonNull Error event) {
            warnListeners(fpc.tools.advanced.chat.event.Error.create(event.error()));
        }

        @Override
        public void visit(@NonNull PostedMessage event) {
        }

        @Override
        public void visit(@NonNull fpc.tools.chat.event.ReceivedMessage event) {
            final Instant receptionTime = event.getReceptionTime();
            Stream.of(event.message().split("\\R"))
                  .map(messageConverter::convert)
                  .flatMap(Optional::stream)
                  .map(m -> new ReceivedMessage<>(receptionTime, m))
                  .forEach(this::dispatchReceivedMessage);
        }

        private void dispatchReceivedMessage(@NonNull ReceivedMessage<M> receivedMessage) {
            listeners.forEachListeners(AdvancedChatListener::onChatEvent, receivedMessage);
            chatReceiver.onMessageReception(receivedMessage);
        }


    }
}
