package fpc.tools.state.chat.state.mutation;

import fpc.tools.advanced.chat.ReceiptSlip;
import fpc.tools.advanced.chat.Request;
import fpc.tools.chat.ChatNotConnected;
import fpc.tools.lang.Futures;
import fpc.tools.state.chat.state.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class SendRequestMutation<A,M> extends VisitorMutation<M> {

    private final Request<A> request;
    private final CompletableFuture<ReceiptSlip<A,M>> future;

    @Override
    public @NonNull ChatState<M> visit(@NonNull ConnectedChat<M> state) {
        Futures.join(state.sendRequest(request),future);
        return super.visit(state);
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull ConnectingChat<M> state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }

    @Override
    public @NonNull ChatState<M> visit(ReconnectingChat<M> state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull DisconnectedChat<M> state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }


}
