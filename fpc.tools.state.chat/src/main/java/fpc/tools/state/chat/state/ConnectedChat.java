package fpc.tools.state.chat.state;

import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.state.chat.state.impl.ConnectedChatImpl;
import lombok.NonNull;

public sealed interface ConnectedChat<M> extends ChatState<M>, AdvancedIO<M> permits ConnectedChatImpl {

    @Override
    default <T> @NonNull T accept(@NonNull Visitor<M,T> visitor) {
        return visitor.visit(this);
    }

    @NonNull ChatState<M> onDisconnectionEvent();

    @NonNull ChatState<M> onDisconnectionRequested();

    @Override
    @NonNull
    default State getState() {
        return State.CONNECTED;
    }
}
