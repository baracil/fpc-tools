package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.chat.state.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class OnConnectionEventMutation extends VisitorMutation {

    @Override
    public @NonNull ChatState visit(@NonNull DisconnectedChat state) {
        LOG.warn("Connection event when in disconnected state ...");
        return state;
    }

    @Override
    public @NonNull ChatState visit(@NonNull ReconnectingChat state) {
        LOG.warn("Connection event when in reconnecting state ...");
        return state;
    }

    @Override
    public @NonNull ChatState visit(@NonNull ConnectingChat state) {
        return state.onConnectionEvent();
    }


    @Override
    public @NonNull ChatState visit(@NonNull ConnectedChat state) {
        return state;
    }
}
