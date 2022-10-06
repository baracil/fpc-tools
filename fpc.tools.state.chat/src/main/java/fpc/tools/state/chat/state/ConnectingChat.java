package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.ConnectingChatImpl;
import lombok.NonNull;

public sealed interface ConnectingChat<M> extends ChatState<M> permits ConnectingChatImpl {

    @Override
    default <T> @NonNull T accept(@NonNull Visitor<M,T> visitor) {
        return visitor.visit(this);
    }

    @NonNull ChatState<M> onDisconnectionEvent();

    @NonNull ChatState<M> onConnectionEvent();

    @NonNull ChatState<M> onDisconnectionRequested();

    @Override
    @NonNull
    default State getState() {
        return State.CONNECTING;
    }
}
