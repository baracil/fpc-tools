package fpc.tools.state.chat.state.impl;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.lang.Subscription;
import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.ChatStateContext;
import fpc.tools.state.chat.state.ConnectingChat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ConnectingChatImpl<M> implements ConnectingChat<M> {

    private final @NonNull ChatStateContext<M> context;
    private final @NonNull AdvancedChat<M> chat;
    private final @NonNull Subscription subscription;

    @Override
    public @NonNull ChatState<M> onDisconnectionEvent() {
        subscription.unsubscribe();
        context.onDisconnected();
        return new DisconnectedChatImpl<>(context);
    }

    @Override
    public @NonNull ChatState<M> onConnectionEvent() {
        try {
            final var connectionOk = context.onConnected(chat);
            return switch (connectionOk) {
                case OK -> new ConnectedChatImpl<>(context.resetNbTries(), chat, subscription);
                case FAILED -> handleFailedConnection();
                case RETRY -> handleRetry();
            };
        } catch (InterruptedException e) {
            chat.requestDisconnection();
            return this;
        }
    }

    private ChatState<M> handleFailedConnection() {
        chat.requestDisconnection();
        return this;
    }

    private ChatState<M> handleRetry() {
        subscription.unsubscribe();
        context.onDisconnected();
        return new DisconnectedChatImpl<>(context.requestReconnection());
    }

    @Override
    public @NonNull ChatState<M> onDisconnectionRequested() {
        chat.requestDisconnection();
        return this;
    }
}
