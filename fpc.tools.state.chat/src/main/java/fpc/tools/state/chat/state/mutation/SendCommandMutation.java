package fpc.tools.state.chat.state.mutation;

import fpc.tools.advanced.chat.Command;
import fpc.tools.advanced.chat.DispatchSlip;
import fpc.tools.chat.ChatNotConnected;
import fpc.tools.lang.Futures;
import fpc.tools.state.chat.state.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class SendCommandMutation<M> extends VisitorMutation<M> {

    private final Command command;
    private final CompletableFuture<DispatchSlip<M>> future;

    @Override
    public @NonNull ChatState<M> visit(@NonNull ConnectedChat<M> state) {
        Futures.join(state.sendCommand(command),future);
        return state;
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull DisconnectedChat<M> state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }

    @Override
    public @NonNull ChatState<M> visit(ReconnectingChat<M> state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull ConnectingChat<M> state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }

}
