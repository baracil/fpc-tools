package fpc.tools.state.chat.state.impl;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.lang.Subscription;
import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.ChatStateContext;
import fpc.tools.state.chat.state.ReconnectingChat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public final class ReconnectingChatImpl<M> implements ReconnectingChat {

    private final ChatStateContext<M> context;

    @Override
    public ChatState onConnectionRequested() {
        Subscription subscription = Subscription.NONE;
        AdvancedChat<M> chat = null;
        try {
            chat = context.createChat();
            subscription = chat.addChatListener(context.getListener());
            context.onConnectionStarted();
            chat.connect();
            return new ConnectingChatImpl<>(context,chat,subscription);
        } catch (RuntimeException error) {
            subscription.unsubscribe();
            Optional.ofNullable(chat).ifPresent(AdvancedChat::requestDisconnection);
            LOG.warn("Fail to connect to chat",error);
            context.onConnectionFailed(error);
            return new DisconnectedChatImpl<>(context);
        }
    }

}
