package fpc.tools.state.chat;

import fpc.tools.state.Mutation;
import fpc.tools.state.chat.state.ChatState;

import java.util.concurrent.CompletionStage;

public interface ChatStateMutator {

    CompletionStage<ChatState> mutate(Mutation<ChatState> mutation);
}
