package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.Mutation;
import fpc.tools.state.chat.state.*;
import lombok.NonNull;

public abstract class VisitorMutation<M> implements Mutation<ChatState<M>>, ChatState.Visitor<M,ChatState<M>> {

    @Override
    public @NonNull ChatState<M> mutate(@NonNull ChatState<M> currentState) {
        return currentState.accept(this);
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull DisconnectedChat<M> state) {
        return state;
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull ConnectingChat<M> state) {
        return state;
    }

    @Override
    public @NonNull ChatState<M> visit(@NonNull ConnectedChat<M> state) {
        return state;
    }

    @Override
    public @NonNull ChatState<M> visit(ReconnectingChat<M> state) {
        return state;
    }
}
