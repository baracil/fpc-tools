package fpc.tools.state.chat.state;

import fpc.tools.advanced.chat.AdvancedIO;
import fpc.tools.state.chat.state.impl.ConnectedChatImpl;

public sealed interface ConnectedChat extends ChatState, AdvancedIO permits ConnectedChatImpl {

    @Override
    default <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    ChatState onDisconnectionEvent();

    ChatState onDisconnectionRequested();

    @Override
    default State getState() {
        return State.CONNECTED;
    }
}
