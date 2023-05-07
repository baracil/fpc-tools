package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.ConnectedChat;
import fpc.tools.state.chat.state.ConnectingChat;
import lombok.NonNull;

public class OnDisconnectionEventMutation extends VisitorMutation {

    @Override
    public ChatState visit(ConnectingChat state) {
        return state.onDisconnectionEvent();
    }

    @Override
    public ChatState visit(ConnectedChat state) {
        return state.onDisconnectionEvent();
    }
}
