package net.femtoparsec.tool.chat;

import fpc.tools.chat.*;
import fpc.tools.chat.event.Connection;
import fpc.tools.chat.event.Disconnection;
import fpc.tools.chat.event.Error;
import fpc.tools.chat.event.ReceivedMessage;
import fpc.tools.lang.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;

/**
 * @author Bastien Aracil
 **/
@Slf4j
public class WebSocketChat extends ChatIOBase implements Chat {

    private final Looper looper;

    private final Instants instants;

    private final AtomicReference<WebSocket> webSocketReference = new AtomicReference<>(null);

    private final SmartLock lock = SmartLock.reentrant();
    private final Condition disconnection = lock.newCondition();

    public WebSocketChat(URI uri, Instants instants) {
        this(uri, ReconnectionPolicy.NO_RECONNECTION, instants);
    }

    public WebSocketChat(URI uri, ReconnectionPolicy policy, Instants instants) {
        this(uri, policy, WaitStrategy.create(), instants);
    }

    public WebSocketChat(
            URI uri,
            ReconnectionPolicy policy,
            WaitStrategy waitStrategy,
            Instants instants) {
        final var action = new ChatLoopAction(uri, policy, waitStrategy);
        this.looper = Looper.simple(action);
        this.instants = instants;
    }

    @Override
    public void connect() {
        looper.start();
    }

    @Override
    public boolean isRunning() {
        return looper.isRunning();
    }

    @Override
    public void requestDisconnection() {
        looper.requestStop();
    }

    @Override
    public void postMessage(String message) {
        final var websocket = webSocketReference.get();
        if (websocket == null) {
            throw new ChatNotConnected();
        }
        try {
            websocket.sendText(message, true).get();
        } catch (InterruptedException e) {
            LOG.warn("Message posting has been interrupted");
        } catch (ExecutionException e) {
            LOG.warn("Message posting failed", e.getCause());
            throw new MessagePostingFailure(message, e.getCause());
        }
    }

    @RequiredArgsConstructor
    private class ChatLoopAction implements LoopAction {

        private final URI uri;

        private final ReconnectionPolicy reconnectionPolicy;

        private final WaitStrategy waitStrategy;


        @Override
        public NextState beforeLooping() {
            this.connect();
            return NextState.CONTINUE;
        }

        private void connect() {
            try {
                webSocketReference.set(null);
                LOG.info("Try websocket connection to {}", uri);
                webSocketReference.set(HttpClient.newHttpClient()
                                                 .newWebSocketBuilder()
                                                 .buildAsync(uri, new ChatEndPoint()).get());

                LOG.info("Websocket connection to {} done", uri);
            } catch (InterruptedException e) {
                LOG.info("Websocket connection to {} has been interrupted", uri);
                throw new ChatConnectionFailure("Websocket connection has been interrupted failed", e);
            } catch (ExecutionException e) {
                LOG.info("Websocket connection to {} has failed", uri);
                throw new ChatConnectionFailure("Websocket connection failed", e);
            }
        }

        @Override
        public NextState performOneIteration() throws Exception {
            this.waitForDisconnection();
            int attemptIndex = 0;
            boolean connected = false;

            while (
                    !connected
                            && reconnectionPolicy.shouldReconnect(attemptIndex)
                            && !Thread.currentThread().isInterrupted()
            ) {
                attemptIndex++;
                LOG.warn("Try reconnection : attempt #{} ", attemptIndex);
                final Duration duration = reconnectionPolicy.delayBeforeNextAttempt(attemptIndex);
                waitStrategy.waitFor(duration);
                connected = tryToConnect();
            }
            LOG.warn("Reconnection successful.");
            return NextState.CONTINUE;
        }

        private void waitForDisconnection() throws InterruptedException {
            lock.await(disconnection);
        }

        private boolean tryToConnect() {
            try {
                connect();
                return true;
            } catch (Exception e) {
                ThrowableTool.interruptIfCausedByInterruption(e);
                LOG.warn("Connection failed", e);
            }
            return false;
        }

        @Override
        public boolean shouldStopOnError(Throwable error) {
            return false;
        }

        @Override
        public void onDone(Throwable error) {
            Optional.ofNullable(webSocketReference.get()).ifPresent(s -> {
                try {
                    s.sendClose(WebSocket.NORMAL_CLOSURE, "Ok").get();
                } catch (Exception e) {
                    ThrowableTool.interruptIfCausedByInterruption(e);
                    LOG.warn("Error while closing websocket", e);
                }
            });
        }

    }


    private class ChatEndPoint implements WebSocket.Listener {

        @Override
        public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
            webSocketReference.set(null);
            warnListeners(Disconnection.create());
            lock.runLocked(disconnection::signalAll);
            return null;
        }

        @Override
        public void onError(WebSocket webSocket, Throwable error) {
            WebSocket.Listener.super.onError(webSocket, error);
            warnListeners(Error.with(error));
        }

        @Override
        public void onOpen(WebSocket webSocket) {
            WebSocket.Listener.super.onOpen(webSocket);
            webSocketReference.set(webSocket);
            warnListeners(Connection.create());
        }

        @Override
        public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
//            displayData(data.toString());
            WebSocket.Listener.super.onText(webSocket,data,last);
            warnListeners(new ReceivedMessage(instants.now(), data.toString()));
            return null;
        }

        private void displayData(String toString) {
            Arrays.stream(toString.split("\\R")).forEach(s -> System.out.println(">>>>  "+s));
        }
    }


}
