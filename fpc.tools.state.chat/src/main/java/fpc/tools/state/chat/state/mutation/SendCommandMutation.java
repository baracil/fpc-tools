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
public class SendCommandMutation extends VisitorMutation {

    private final Command command;
    private final CompletableFuture<DispatchSlip> future;

    @Override
    public @NonNull ChatState visit(@NonNull ConnectedChat state) {
        Futures.join(state.sendCommand(command),future);
        return state;
    }

    @Override
    public @NonNull ChatState visit(@NonNull DisconnectedChat state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }

    @Override
    public @NonNull ChatState visit(ReconnectingChat state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }

    @Override
    public @NonNull ChatState visit(@NonNull ConnectingChat state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }

}
