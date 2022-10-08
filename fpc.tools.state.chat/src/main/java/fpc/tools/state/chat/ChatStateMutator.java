package fpc.tools.state.chat;

import fpc.tools.state.Mutation;
import fpc.tools.state.chat.state.ChatState;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface ChatStateMutator {

    @NonNull CompletionStage<ChatState> mutate(@NonNull Mutation<ChatState> mutation);
}
