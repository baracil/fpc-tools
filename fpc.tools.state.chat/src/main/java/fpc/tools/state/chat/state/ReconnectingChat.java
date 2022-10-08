package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.ReconnectingChatImpl;
import lombok.NonNull;

public sealed interface ReconnectingChat extends ChatState permits ReconnectingChatImpl {

    @NonNull ChatState onConnectionRequested();

    @Override
    default <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    @NonNull
    default State getState() {
        return State.RECONNECTING;
    }
}
