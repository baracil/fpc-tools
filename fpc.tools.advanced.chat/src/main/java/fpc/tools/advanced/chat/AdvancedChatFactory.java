package fpc.tools.advanced.chat;

import fpc.tools.chat.Chat;
import fpc.tools.lang.Instants;
import fpc.tools.lang.ServiceLoaderHelper;

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
    public abstract <M> AdvancedChat<M> createBasedOn(
            Chat chat,
            RequestAnswerMatcher<M> matcher,
            MessageConverter<M> messageConverter,
            Instants instants
    );

    /**
     *
     */
    public static <M> AdvancedChat<M> createAdvancedChatBasedOn(
            Chat chat,
            RequestAnswerMatcher<M> matcher,
            MessageConverter<M> messageConverter,
            Instants instants
    ) {
        return getInstance().createBasedOn(chat, matcher, messageConverter, instants);
    }

    public static AdvancedChatFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {

        private static final AdvancedChatFactory INSTANCE = ServiceLoaderHelper.load(ServiceLoader.load(AdvancedChatFactory.class));
    }

}
