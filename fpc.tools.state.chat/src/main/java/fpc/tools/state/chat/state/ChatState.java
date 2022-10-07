package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.DisconnectedChatImpl;
import lombok.NonNull;

public sealed interface ChatState<M> permits DisconnectedChat, ConnectedChat, ConnectingChat, ReconnectingChat {

    <T> @NonNull T accept(@NonNull Visitor<M,T> visitor);

    default void onEnter(@NonNull State oldState) {}

    @NonNull State getState();


    interface Visitor<M,T> {
        @NonNull T visit(@NonNull DisconnectedChat<M> state);

        @NonNull T visit(@NonNull ConnectingChat<M> state);

        @NonNull T visit(@NonNull ConnectedChat<M> state);

        @NonNull T visit(ReconnectingChat<M> state);
    }


    static <M> ChatState<M> createInitial(@NonNull ChatStateContext<M> context) {
        return new DisconnectedChatImpl<>(context);
    }



}
