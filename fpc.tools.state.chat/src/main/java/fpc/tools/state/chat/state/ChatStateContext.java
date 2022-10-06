package fpc.tools.state.chat.state;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.advanced.chat.AdvancedChatListener;
import fpc.tools.state.chat.ChatInfo;
import fpc.tools.state.chat.ChatStateMutator;
import fpc.tools.state.chat.OnConnectedResult;
import fpc.tools.state.chat.state.mutation.ConnectMutation;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ChatStateContext<M> {
    private final @NonNull ChatStateMutator<M> mutator;
    private final @NonNull ChatInfo<M> chatInfo;
    private final @NonNull AdvancedChatListener<M> listener;
    private final int nbTries;

    public ChatStateContext(@NonNull ChatStateMutator<M> mutator, @NonNull ChatInfo<M> chatInfo, @NonNull AdvancedChatListener<M> listener) {
        this.mutator = mutator;
        this.chatInfo = chatInfo;
        this.listener = listener;
        this.nbTries = 0;
    }

    public @NonNull AdvancedChat<M> createChat() {
        return chatInfo.createChat();
    }


    public @NonNull ChatStateContext<M> resetNbTries() {
        return new ChatStateContext<>(mutator,chatInfo,listener,0);
    }

    public @NonNull ChatStateContext<M> requestReconnection() {
        final var nextContext = new ChatStateContext<>(mutator,chatInfo,listener,nbTries+1);
        mutator.mutate(new ConnectMutation<>());
        return nextContext;
    }

    public void onConnectionStarted() {
        chatInfo.onConnectionStarted();
    }

    public void onConnectionFailed(@NonNull RuntimeException error) {
        chatInfo.onConnectionFailed(error);
    }

    public void onDisconnected() {
        chatInfo.onDisconnected();
    }

    public @NonNull OnConnectedResult onConnected(@NonNull AdvancedChat<M> chat) throws InterruptedException {
        return chatInfo.onConnected(chat, nbTries);
    }
}
