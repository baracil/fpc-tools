package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.DisconnectedChatImpl;
import lombok.NonNull;

public sealed interface DisconnectedChat extends ChatState permits DisconnectedChatImpl {

    @NonNull ChatState onConnectionRequested();

    @Override
    default <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    @NonNull
    default State getState() {
        return State.DISCONNECTED;
    }
}
