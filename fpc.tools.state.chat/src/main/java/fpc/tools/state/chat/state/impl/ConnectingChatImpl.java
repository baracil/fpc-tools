package fpc.tools.state.chat.state.impl;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.lang.Subscription;
import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.ChatStateContext;
import fpc.tools.state.chat.state.ConnectingChat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ConnectingChatImpl<M> implements ConnectingChat {

    private final @NonNull ChatStateContext<M> context;
    private final @NonNull AdvancedChat<M> chat;
    private final @NonNull Subscription subscription;

    @Override
    public @NonNull ChatState onDisconnectionEvent() {
        subscription.unsubscribe();
        context.onDisconnected();
        return new DisconnectedChatImpl<>(context);
    }

    @Override
    public @NonNull ChatState onConnectionEvent() {
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

    private ChatState handleFailedConnection() {
        chat.requestDisconnection();
        return this;
    }

    private ChatState handleRetry() {
        subscription.unsubscribe();
        context.onRetry();
        return new DisconnectedChatImpl<>(context.requestReconnection());
    }

    @Override
    public @NonNull ChatState onDisconnectionRequested() {
        chat.requestDisconnection();
        return this;
    }
}
