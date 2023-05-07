package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.DisconnectedChatImpl;
import lombok.NonNull;

public sealed interface DisconnectedChat extends ChatState permits DisconnectedChatImpl {

    ChatState onConnectionRequested();

    @Override
    default <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    default State getState() {
        return State.DISCONNECTED;
    }
}
