package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.chat.state.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class OnConnectionEventMutation<M> extends VisitorMutation<M> {

    @Override
    public @NonNull ChatState<M> visit(@NonNull DisconnectedChat<M> state) {
        LOG.warn("Connection event when in disconnected state ...");
        return state;
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull ReconnectingChat<M> state) {
        LOG.warn("Connection event when in reconnecting state ...");
        return state;
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull ConnectingChat<M> state) {
        return state.onConnectionEvent();
    }


    @Override
    public @NonNull ChatState<M> visit(@NonNull ConnectedChat<M> state) {
        return state;
    }
}
