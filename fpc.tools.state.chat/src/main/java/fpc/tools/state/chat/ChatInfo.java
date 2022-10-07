package fpc.tools.state.chat;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.advanced.chat.AdvancedChatFactory;
import fpc.tools.advanced.chat.MessageConverter;
import fpc.tools.advanced.chat.RequestAnswerMatcher;
import fpc.tools.chat.*;
import fpc.tools.lang.Instants;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.net.URI;

@RequiredArgsConstructor
public class ChatInfo<M> implements ChatStateListener<M> {

    @NonNull URI address;
    @NonNull RequestAnswerMatcher<M> matcher;
    @NonNull MessageConverter<M> messageConverter;
    @NonNull ReconnectionPolicy reconnectionPolicy;
    @NonNull Instants instants;
    @Delegate
    @NonNull ChatStateListener<M> chatStateListener;
    BandwidthLimits bandwidthLimits;

    public ChatInfo(@NonNull URI address, @NonNull RequestAnswerMatcher<M> matcher, @NonNull MessageConverter<M> messageConverter, @NonNull ReconnectionPolicy reconnectionPolicy, @NonNull Instants instants, @NonNull ChatStateListener<M> chatStateListener, @NonNull BandwidthLimits bandwidthLimits) {
        this.address = address;
        this.matcher = matcher;
        this.messageConverter = messageConverter;
        this.reconnectionPolicy = reconnectionPolicy;
        this.instants = instants;
        this.chatStateListener = chatStateListener;
        this.bandwidthLimits = bandwidthLimits;
    }


    public @NonNull AdvancedChat<M> createChat() {
        final var advancedChatFactory = AdvancedChatFactory.getInstance();
        final var chat = createSimpleChat();
        return advancedChatFactory.createBasedOn(chat, matcher, messageConverter, instants);
    }

    private Chat createSimpleChat() {
        var chatFactory = ChatFactory.getInstance();
        var chat = chatFactory.create(address, reconnectionPolicy, instants);
        if (bandwidthLimits == null) {
            return chat;
        }
        return new ThrottledChat(chat, bandwidthLimits);
    }

}
