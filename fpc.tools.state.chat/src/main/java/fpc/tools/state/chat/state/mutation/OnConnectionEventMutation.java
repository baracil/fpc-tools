package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.chat.state.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class OnConnectionEventMutation extends VisitorMutation {

    @Override
    public ChatState visit(DisconnectedChat state) {
        LOG.warn("Connection event when in disconnected state ...");
        return state;
    }

    @Override
    public ChatState visit(ReconnectingChat state) {
        LOG.warn("Connection event when in reconnecting state ...");
        return state;
    }

    @Override
    public ChatState visit(ConnectingChat state) {
        return state.onConnectionEvent();
    }


    @Override
    public ChatState visit(ConnectedChat state) {
        return state;
    }
}
