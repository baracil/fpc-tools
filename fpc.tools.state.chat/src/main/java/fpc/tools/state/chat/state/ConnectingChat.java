package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.ConnectingChatImpl;
import lombok.NonNull;

public sealed interface ConnectingChat extends ChatState permits ConnectingChatImpl {

    @Override
    default <T> @NonNull T accept(@NonNull Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull ChatState onDisconnectionEvent();

    @NonNull ChatState onConnectionEvent();

    @NonNull ChatState onDisconnectionRequested();

    @Override
    @NonNull
    default State getState() {
        return State.CONNECTING;
    }
}
