package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.AdvancedChatListener;
import fpc.tools.advanced.chat.event.Error;
import fpc.tools.advanced.chat.event.PostedMessage;
import fpc.tools.chat.ChatIO;
import fpc.tools.chat.ChatNotConnected;
import fpc.tools.lang.Instants;
import fpc.tools.lang.Listeners;
import fpc.tools.lang.LoopAction;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author Bastien Aracil
 **/
@RequiredArgsConstructor
public class ChatSender<M> implements LoopAction {

    /**
     * The chat i/o used to send the message
     */
    @NonNull
    private final ChatIO chat;

    /**
     * the list of listeners of chat events.
     */
    @NonNull
    private final Listeners<AdvancedChatListener<M>> listeners;

    /**
     * contains the list of requests waiting for an answer from the chat
     */
    @NonNull
    private final BlockingDeque<RequestPostData<?,M>> requestPostDataQueue;

    /**
     * The queue of pending message to send
     */
    @NonNull
    private final BlockingDeque<PostData<?,M>> postQueue = new LinkedBlockingDeque<>();

    private final @NonNull Instants instants;

    private final Timer timer = new Timer("Request timeout", true);

    @NonNull
    @Getter
    @Setter
    private Duration timeout = Duration.ofMinutes(1);

    private volatile boolean running = false;

    @Override
    public @NonNull NextState beforeLooping() {
        running = true;
        return NextState.CONTINUE;
    }

    @Override
    public void onDone(Throwable error) {
        running = false;
    }

    public <A> CompletionStage<A> send(@NonNull PostData<A,M> postData) {
        if (running) {
            postQueue.add(postData);
            return postData.completionStage();
        } else {
            return CompletableFuture.failedFuture(new ChatNotConnected());
        }
    }

    @Override
    public @NonNull NextState performOneIteration() throws Exception {
        final PostData<?,M> postData = postQueue.takeFirst();

        try {
            postData.asRequestPostData().ifPresent(this::handleRequestPostData);
            final Instant dispatchingTime = instants.now();
            this.postMessageToChat(postData);
            this.performActionsAfterSuccessfulPost(postData,dispatchingTime);
        } catch (Throwable t) {
            this.performActionsAfterFailedPost(postData,t);
            throw t;
        }

        return NextState.CONTINUE;
    }

    private void handleRequestPostData(@NonNull RequestPostData<?,M> requestPostData) {
        this.addRequestPostDataToQueue(requestPostData);
        this.addTimeoutTimerForRequest(requestPostData);
    }

    private void addRequestPostDataToQueue(@NonNull RequestPostData<?,M> requestPostData) {
        requestPostDataQueue.offerLast(requestPostData);
    }

    protected void addTimeoutTimerForRequest(@NonNull RequestPostData<?,M> requestPostData) {
        final Duration timeout = this.timeout;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                requestPostData.onRequestTimeout(timeout);
            }
        },timeout.toMillis());
    }

    private void postMessageToChat(@NonNull PostData<?,M> postData) {
        final var dispatchContext = instants.now();
        chat.postMessage(postData.messagePayload(dispatchContext));
    }

    private void performActionsAfterSuccessfulPost(@NonNull PostData<?,M> postData, @NonNull Instant dispatchingTime) {
        final var postedMessage = new PostedMessage<M>(dispatchingTime, postData.message());
        listeners.forEachListeners(AdvancedChatListener::onChatEvent, postedMessage);
        postData.onMessagePosted(dispatchingTime);
    }

    private void performActionsAfterFailedPost(@NonNull PostData<?,M> postData, @NonNull Throwable t) {
        final var error = new Error<M>(t);
        listeners.forEachListeners(AdvancedChatListener::onChatEvent,error);
        postData.onMessagePostFailure(t);
    }

}
