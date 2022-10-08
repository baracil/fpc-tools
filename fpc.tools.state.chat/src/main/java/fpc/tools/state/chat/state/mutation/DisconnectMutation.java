package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.ConnectedChat;
import fpc.tools.state.chat.state.ConnectingChat;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisconnectMutation extends VisitorMutation {

    @Override
    public @NonNull ChatState visit(@NonNull ConnectingChat state) {
        return state.onDisconnectionRequested();
    }

    @Override
    public @NonNull ChatState visit(@NonNull ConnectedChat state) {
        return state.onDisconnectionRequested();
    }
}
