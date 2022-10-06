package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.ReconnectingChatImpl;
import lombok.NonNull;

public sealed interface ReconnectingChat<M> extends ChatState<M> permits ReconnectingChatImpl {

    @NonNull ChatState<M> onConnectionRequested();

    @Override
    default <T> @NonNull T accept(@NonNull Visitor<M, T> visitor) {
        return visitor.visit(this);
    }

    @Override
    @NonNull
    default State getState() {
        return State.RECONNECTING;
    }
}
