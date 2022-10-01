package fpc.tools.state.chat;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.advanced.chat.AdvancedChatFactory;
import fpc.tools.advanced.chat.MessageConverter;
import fpc.tools.advanced.chat.RequestAnswerMatcher;
import fpc.tools.chat.ChatFactory;
import fpc.tools.chat.ReconnectionPolicy;
import fpc.tools.lang.Instants;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@RequiredArgsConstructor
public class ChatInfo<M> {

    @NonNull URI address;
    @NonNull RequestAnswerMatcher<M> matcher;
    @NonNull MessageConverter<M> messageConverter;
    @NonNull ReconnectionPolicy reconnectionPolicy;
    @NonNull Instants instants;

    @Getter
    @NonNull ChatManager<M> chatManager;

    public @NonNull AdvancedChat<M> createChat() {
        @NonNull var advancedChatFactory = AdvancedChatFactory.getInstance();
        @NonNull var chatFactory = ChatFactory.getInstance();
        return advancedChatFactory.createBasedOn(chatFactory.create(address, reconnectionPolicy, instants),matcher,messageConverter,instants);
    }


}
