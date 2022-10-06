package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.DisconnectedChatImpl;
import lombok.NonNull;

public sealed interface DisconnectedChat<M> extends ChatState<M> permits DisconnectedChatImpl {

    @NonNull ChatState<M> onConnectionRequested();

    @Override
    default <T> @NonNull T accept(@NonNull Visitor<M, T> visitor) {
        return visitor.visit(this);
    }

    @Override
    @NonNull
    default State getState() {
        return State.DISCONNECTED;
    }
}