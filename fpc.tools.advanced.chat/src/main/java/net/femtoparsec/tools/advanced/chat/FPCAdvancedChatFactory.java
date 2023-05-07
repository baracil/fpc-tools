package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.advanced.chat.AdvancedChatFactory;
import fpc.tools.advanced.chat.MessageConverter;
import fpc.tools.advanced.chat.RequestAnswerMatcher;
import fpc.tools.chat.Chat;
import fpc.tools.lang.Instants;
import fpc.tools.lang.Priority;

/**
 * @author Bastien Aracil
 **/
@Priority(Integer.MIN_VALUE)
public class FPCAdvancedChatFactory extends AdvancedChatFactory {

    @Override
    public <M> AdvancedChat<M> createBasedOn(Chat chat,
                                                      RequestAnswerMatcher<M> matcher,
                                                      MessageConverter<M> messageConverter,
                                                      Instants instants) {
        return new FPCAdvancedChat<>(chat, matcher, messageConverter, instants);
    }

}
