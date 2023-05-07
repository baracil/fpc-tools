package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.DisconnectedChat;
import fpc.tools.state.chat.state.ReconnectingChat;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectMutation extends VisitorMutation {


    @Override
    public ChatState visit(DisconnectedChat state) {
        return state.onConnectionRequested();
    }

    @Override
    public ChatState visit(ReconnectingChat state) {
        return state.onConnectionRequested();
    }
}
