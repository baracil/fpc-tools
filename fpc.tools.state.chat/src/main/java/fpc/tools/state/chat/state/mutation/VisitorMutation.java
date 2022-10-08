package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.Mutation;
import fpc.tools.state.chat.state.*;
import lombok.NonNull;

public abstract class VisitorMutation implements Mutation<ChatState>, ChatState.Visitor<ChatState> {

    @Override
    public @NonNull ChatState mutate(@NonNull ChatState currentState) {
        return currentState.accept(this);
    }

    @Override
    public @NonNull ChatState visit(@NonNull DisconnectedChat state) {
        return state;
    }

    @Override
    public @NonNull ChatState visit(@NonNull ConnectingChat state) {
        return state;
    }

    @Override
    public @NonNull ChatState visit(@NonNull ConnectedChat state) {
        return state;
    }

    @Override
    public @NonNull ChatState visit(ReconnectingChat state) {
        return state;
    }
}
