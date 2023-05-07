package fpc.tools.state.chat.state.impl;

import fpc.tools.advanced.chat.*;
import fpc.tools.lang.Subscription;
import fpc.tools.state.chat.state.ChatState;
import fpc.tools.state.chat.state.ChatStateContext;
import fpc.tools.state.chat.state.ConnectedChat;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public final class ConnectedChatImpl<M> implements ConnectedChat {

    private final ChatStateContext<M> context;
    private final AdvancedChat<M> chat;
    private final Subscription subscription;


    @Override
    public ChatState onDisconnectionEvent() {
        subscription.unsubscribe();
        context.onDisconnected();
        return new DisconnectedChatImpl<>(context);
    }

    @Override
    public ChatState onDisconnectionRequested() {
        chat.requestDisconnection();
        return this;
    }

    @Override
    public CompletionStage<DispatchSlip> sendCommand(Command command) {
        return chat.sendCommand(command);
    }

    @Override
    public <A> CompletionStage<ReceiptSlip<A>> sendRequest(Request<A> request) {
        return chat.sendRequest(request);
    }
}
