package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.ConnectedChat;
import fpc.tools.state.chat.state.ConnectingChat;
import lombok.NonNull;

public class OnDisconnectionEventMutation<M> extends VisitorMutation<M> {

    @Override
    public @NonNull ChatState<M> visit(@NonNull ConnectingChat<M> state) {
        return state.onDisconnectionEvent();
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull ConnectedChat<M> state) {
        return state.onDisconnectionEvent();
    }
}
