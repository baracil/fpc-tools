package fpc.tools.state.chat.state.mutation;

import fpc.tools.state.Mutation;
import fpc.tools.state.chat.state.*;

public abstract class VisitorMutation implements Mutation<ChatState>, ChatState.Visitor<ChatState> {

    @Override
    public ChatState mutate(ChatState currentState) {
        return currentState.accept(this);
    }

    @Override
    public ChatState visit(DisconnectedChat state) {
        return state;
    }

    @Override
    public ChatState visit(ConnectingChat state) {
        return state;
    }

    @Override
    public ChatState visit(ConnectedChat state) {
        return state;
    }

    @Override
    public ChatState visit(ReconnectingChat state) {
        return state;
    }
}
