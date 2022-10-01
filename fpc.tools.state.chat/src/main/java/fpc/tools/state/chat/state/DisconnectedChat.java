package fpc.tools.state.chat.state;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.advanced.chat.AdvancedChatListener;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.ThrowableTool;
import fpc.tools.state.chat.ChatInfo;
import fpc.tools.state.chat.ChatState;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DisconnectedChat<M> implements ChatState<M> {

    private final @NonNull ChatInfo<M> chatInfo;
    private final @NonNull AdvancedChatListener<M> listener;

    @Override
    public @NonNull ChatState<M> connect() {
        Subscription subscription = Subscription.NONE;
        AdvancedChat<M> chat = null;
        try {
            chat = chatInfo.createChat();
            subscription = chat.addChatListener(listener);
            chat.connect();
            chatInfo.getChatManager().onConnected(chat);
            return new ConnectedChat<>(chatInfo, chat, listener, subscription);
        } catch (Throwable t) {
            ThrowableTool.interruptIfCausedByInterruption(t);
            subscription.unsubscribe();
            Optional.ofNullable(chat).ifPresent(AdvancedChat::requestDisconnection);
            chatInfo.getChatManager().onDisconnected();
            return this;
        }
    }


    @Override
    public @NonNull ChatState<M> disconnect() {
        return this;
    }
}
