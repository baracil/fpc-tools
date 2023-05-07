package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.ConnectingChatImpl;

public sealed interface ConnectingChat extends ChatState permits ConnectingChatImpl {

    @Override
    default <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    ChatState onDisconnectionEvent();

    ChatState onConnectionEvent();

    ChatState onDisconnectionRequested();

    @Override
    default State getState() {
        return State.CONNECTING;
    }
}
