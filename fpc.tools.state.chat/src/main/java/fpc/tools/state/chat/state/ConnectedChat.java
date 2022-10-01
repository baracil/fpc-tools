package fpc.tools.state.chat.state;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.advanced.chat.AdvancedChatListener;
import fpc.tools.lang.Subscription;
import fpc.tools.state.chat.ChatInfo;
import fpc.tools.state.chat.ChatState;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConnectedChat<M> implements ChatState<M> {

    private final @NonNull ChatInfo<M> chatInfo;
    private final @NonNull AdvancedChat<M> chat;
    private final @NonNull AdvancedChatListener<M> listener;
    private final @NonNull Subscription subscription;

    @Override
    public @NonNull ChatState<M> connect() {
        return this;
    }

    @Override
    public @NonNull ChatState<M> disconnect() {
        chatInfo.getChatManager().onDisconnected();
        subscription.unsubscribe();
        chat.requestDisconnection();
        return new DisconnectedChat<>(chatInfo,listener);
    }
}
