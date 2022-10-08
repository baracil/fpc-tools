package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.ConnectedChat;
import fpc.tools.state.chat.state.ConnectingChat;
import lombok.NonNull;

public class OnDisconnectionEventMutation extends VisitorMutation {

    @Override
    public @NonNull ChatState visit(@NonNull ConnectingChat state) {
        return state.onDisconnectionEvent();
    }

    @Override
    public @NonNull ChatState visit(@NonNull ConnectedChat state) {
        return state.onDisconnectionEvent();
    }
}
