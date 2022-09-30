package fpc.tools.advanced.chat;

import fpc.tools.chat.Chat;
import fpc.tools.lang.Instants;
import fpc.tools.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

/**
 * @author Bastien Aracil
 **/
public abstract class AdvancedChatFactory {

    /**
     * Create an {@link AdvancedChat} base on the provided {@link Chat}.
     *
     * @param chat the chat to used to create the <code>AdvancedChat</code>
     * @param matcher the matcher used to match request and message from the chat
     * @param messageConverter used to convert text message to the type of the message of the chat
     * @param <M> the type of the message of the chat

     * @return an <code>AdvancedChat</code> base on the provided parameters
     */
    @NonNull
    public abstract <M> AdvancedChat<M> createBasedOn(
            @NonNull Chat chat,
            @NonNull RequestAnswerMatcher<M> matcher,
            @NonNull MessageConverter<M> messageConverter,
            @NonNull Instants instants
    );

    /**
     *
     */
    @NonNull
    public static <M> AdvancedChat<M> createAdvancedChatBasedOn(
            @NonNull Chat chat,
            @NonNull RequestAnswerMatcher<M> matcher,
            @NonNull MessageConverter<M> messageConverter,
            @NonNull Instants instants
    ) {
        return getInstance().createBasedOn(chat, matcher, messageConverter, instants);
    }

    @NonNull
    public static AdvancedChatFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {

        private static final AdvancedChatFactory INSTANCE = ServiceLoaderHelper.load(ServiceLoader.load(AdvancedChatFactory.class));
    }

}
