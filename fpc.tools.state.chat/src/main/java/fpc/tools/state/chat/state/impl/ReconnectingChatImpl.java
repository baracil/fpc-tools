package fpc.tools.state.chat.state.impl;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.lang.Subscription;
import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.ChatStateContext;
import fpc.tools.state.chat.state.ReconnectingChat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public final class ReconnectingChatImpl<M> implements ReconnectingChat<M> {

    private final @NonNull ChatStateContext<M> context;

    @Override
    public @NonNull ChatState<M> onConnectionRequested() {
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


    //    @NonNull ChatState<M> connect(boolean retry) {
//        Subscription subscription = Subscription.NONE;
//        AdvancedChat<M> chat = null;
//        try {
//            chat = chatInfo.createChat();
//            subscription = chat.addChatListener(listener);
//            if (retry) {
//                chatInfo.prepareRetry();
//            } else {
//                chatInfo.onConnectionStarted();
//            }
//            chat.connect();
//            return new Connector<>(chatInfo,listener,chat,subscription).performConnection();
//        } catch (RuntimeException t) {
//            subscription.unsubscribe();
//            Optional.ofNullable(chat).ifPresent(AdvancedChat::requestDisconnection);
//            LOG.warn("Fail to connect to chat",t);
//            chatInfo.canRetryIfFailed(t);
//            chatInfo.onDisconnected();
//            return new DisconnectedChatImpl<>(chatInfo,listener,t);
//        }
//    }
//
//    @Override
//    public @NonNull ChatState<M> connect() {
//        return connect(false);
//    }





}
