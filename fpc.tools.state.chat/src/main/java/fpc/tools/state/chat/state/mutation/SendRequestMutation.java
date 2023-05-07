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
public class SendRequestMutation<A> extends VisitorMutation {

    private final Request<A> request;
    private final CompletableFuture<ReceiptSlip<A>> future;

    @Override
    public ChatState visit(ConnectedChat state) {
        Futures.join(state.sendRequest(request),future);
        return super.visit(state);
    }

    @Override
    public ChatState visit(ConnectingChat state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }

    @Override
    public ChatState visit(ReconnectingChat state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }

    @Override
    public ChatState visit(DisconnectedChat state) {
        future.completeExceptionally(new ChatNotConnected());
        return state;
    }


}
