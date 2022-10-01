package fpc.tools.state.chat;

import fpc.tools.advanced.chat.AdvancedChatListener;
import fpc.tools.advanced.chat.event.Error;
import fpc.tools.advanced.chat.event.*;
import fpc.tools.state.Identity;
import fpc.tools.state.Mutation;
import lombok.NonNull;

public class ChatStateManager<M> {

    private final Identity<ChatState<M>> state;

    public ChatStateManager(@NonNull ChatInfo<M> chatInfo) {
        this.state = Identity.create(ChatState.createDisconnected(chatInfo, new Listener()));
    }

    public void connect() {
        state.mutate(ChatState::connect);
    }
    public void disconnect() {
        state.mutate(ChatState::disconnect);
    }


    private class Listener implements AdvancedChatListener<M>, AdvancedChatEventVisitor<M, Mutation<ChatState<M>>> {
        @Override
        public void onChatEvent(@NonNull AdvancedChatEvent<M> chatEvent) {
            chatEvent.accept(this);
        }

        @Override
        public @NonNull Mutation<ChatState<M>> visit(@NonNull Connection<M> event) {
            return ChatState::connect;
        }

        @Override
        public @NonNull Mutation<ChatState<M>> visit(@NonNull Disconnection<M> event) {
            return ChatState::disconnect;
        }

        @Override
        public @NonNull Mutation<ChatState<M>> visit(@NonNull PostedMessage<M> event) {
            return Mutation.identity();
        }

        @Override
        public @NonNull Mutation<ChatState<M>> visit(@NonNull ReceivedMessage<M> event) {
            return Mutation.identity();
        }

        @Override
        public @NonNull Mutation<ChatState<M>> visit(@NonNull Error<M> event) {
            return Mutation.identity();
        }
    }
}
