package fpc.tools.state.chat.state;

import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.state.chat.state.impl.ConnectedChatImpl;
import lombok.NonNull;

public sealed interface ConnectedChat extends ChatState, AdvancedIO permits ConnectedChatImpl {

    @Override
    default <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull ChatState onDisconnectionEvent();

    @NonNull ChatState onDisconnectionRequested();

    @Override
    @NonNull
    default State getState() {
        return State.CONNECTED;
    }
}
