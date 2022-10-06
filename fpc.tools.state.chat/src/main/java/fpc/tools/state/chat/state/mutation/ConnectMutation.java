package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.DisconnectedChat;
import fpc.tools.state.chat.state.ReconnectingChat;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectMutation<M> extends VisitorMutation<M> {


    @Override
    public @NonNull ChatState<M> visit(@NonNull DisconnectedChat<M> state) {
        return state.onConnectionRequested();
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull ReconnectingChat<M> state) {
        return state.onConnectionRequested();
    }
}
