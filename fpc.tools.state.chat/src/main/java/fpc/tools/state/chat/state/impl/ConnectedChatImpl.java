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
public final class ConnectedChatImpl<M> implements ConnectedChat<M> {

    private final @NonNull ChatStateContext<M> context;
    private final @NonNull AdvancedChat<M> chat;
    private final @NonNull Subscription subscription;


    @Override
    public @NonNull ChatState<M> onDisconnectionEvent() {
        subscription.unsubscribe();
        context.onDisconnected();
        return new DisconnectedChatImpl<>(context);
    }

    @Override
    public @NonNull ChatState<M> onDisconnectionRequested() {
        chat.requestDisconnection();
        return this;
    }

    @Override
    public @NonNull CompletionStage<DispatchSlip<M>> sendCommand(@NonNull Command command) {
        return chat.sendCommand(command);
    }

    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A, M>> sendRequest(@NonNull Request<A> request) {
        return chat.sendRequest(request);
    }
}
