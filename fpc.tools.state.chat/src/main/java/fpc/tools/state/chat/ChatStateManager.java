package fpc.tools.state.chat;

import fpc.tools.advanced.chat.*;
import fpc.tools.advanced.chat.event.AdvancedChatEvent;
import fpc.tools.advanced.chat.event.AdvancedChatEventAdapter;
import fpc.tools.advanced.chat.event.Connection;
import fpc.tools.advanced.chat.event.Disconnection;
import fpc.tools.state.Identity;
import fpc.tools.state.Mutation;
import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.ChatStateContext;
import fpc.tools.state.chat.state.mutation.*;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ChatStateManager<M> implements AdvancedIO<M> {

    private final Identity<ChatState<M>> stat2e;

    public ChatStateManager(@NonNull ChatInfo<M> chatInfo) {
        this.stat2e = Identity.create(i -> ChatState.createInitial(new ChatStateContext<>(this::mutate,chatInfo, new Listener())));
    }

    public CompletionStage<ChatStateManager<M>> connect() {
        return this.mutate(new ConnectMutation<>()).thenApply(s -> this);
    }

    public CompletionStage<ChatStateManager<M>> disconnect() {
        return this.mutate(new DisconnectMutation<>()).thenApply(s -> this);
    }

    @Override
    public @NonNull CompletionStage<DispatchSlip<M>> sendCommand(@NonNull Command command) {
        final var future = new CompletableFuture<DispatchSlip<M>>();
        final var mutation = new SendCommandMutation<M>(command,future);
        this.mutate(mutation);
        return future;
    }

    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A, M>> sendRequest(@NonNull Request<A> request) {
        final var future = new CompletableFuture<ReceiptSlip<A,M>>();
        final var mutation = new SendRequestMutation<A,M>(request,future);
        this.mutate(mutation);
        return future;
    }

    private class Listener extends AdvancedChatEventAdapter<M,Mutation<ChatState<M>>> implements AdvancedChatListener<M> {
        @Override
        public void onChatEvent(@NonNull AdvancedChatEvent<M> chatEvent) {
            final var mutation = chatEvent.accept(this);
            mutate(mutation);
        }

        @Override
        protected Mutation<ChatState<M>> fallback(@NonNull AdvancedChatEvent<M> event) {
            return s -> s;
        }

        @Override
        public @NonNull Mutation<ChatState<M>> visit(@NonNull Connection<M> event) {
            return new OnConnectionEventMutation<>();
        }

        @Override
        public @NonNull Mutation<ChatState<M>> visit(@NonNull Disconnection<M> event) {
            return new OnDisconnectionEventMutation<>();
        }
    }

    private @NonNull CompletionStage<ChatState<M>> mutate(@NonNull Mutation<ChatState<M>> mutation) {
        return stat2e.mutate(mutation, (o,n) -> {
            if (o.getState() != n.getState()) {
                n.onEnter(o.getState());
            }
            return CompletableFuture.completedFuture(n);
        });
    }
}
