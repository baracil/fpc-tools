package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.ReconnectingChatImpl;

public sealed interface ReconnectingChat extends ChatState permits ReconnectingChatImpl {

    ChatState onConnectionRequested();

    @Override
    default <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    default State getState() {
        return State.RECONNECTING;
    }
}
