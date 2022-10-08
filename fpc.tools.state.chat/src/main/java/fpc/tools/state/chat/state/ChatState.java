package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.DisconnectedChatImpl;
import lombok.NonNull;

public sealed interface ChatState permits DisconnectedChat, ConnectedChat, ConnectingChat, ReconnectingChat {

    <T> @NonNull T accept(@NonNull Visitor<T> visitor);

    default void onEnter(@NonNull State oldState) {}

    @NonNull State getState();


    interface Visitor<T> {
        @NonNull T visit(@NonNull DisconnectedChat state);

        @NonNull T visit(@NonNull ConnectingChat state);

        @NonNull T visit(@NonNull ConnectedChat state);

        @NonNull T visit(ReconnectingChat state);
    }


    static <M> ChatState createInitial(@NonNull ChatStateContext<M> context) {
        return new DisconnectedChatImpl<>(context);
    }



}
