package fpc.tools.state.chat.state;

import fpc.tools.state.chat.state.impl.DisconnectedChatImpl;
import lombok.NonNull;

public sealed interface ChatState permits DisconnectedChat, ConnectedChat, ConnectingChat, ReconnectingChat {

    <T> T accept(Visitor<T> visitor);

    default void onEnter(State oldState) {}

    State getState();


    interface Visitor<T> {
        T visit(DisconnectedChat state);

        T visit(ConnectingChat state);

        T visit(ConnectedChat state);

        T visit(ReconnectingChat state);
    }


    static <M> ChatState createInitial(ChatStateContext<M> context) {
        return new DisconnectedChatImpl<>(context);
    }



}
