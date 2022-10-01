package fpc.tools.state.chat;

import fpc.tools.advanced.chat.AdvancedChatListener;
import fpc.tools.state.chat.state.DisconnectedChat;
import lombok.NonNull;

public interface ChatState<M> {

    static <M> ChatState<M> createDisconnected(@NonNull ChatInfo<M> chatInfo, @NonNull AdvancedChatListener<M> listener) {
        return new DisconnectedChat<>(chatInfo,listener);
    }

    @NonNull ChatState<M> connect();

    @NonNull ChatState<M> disconnect();

}
