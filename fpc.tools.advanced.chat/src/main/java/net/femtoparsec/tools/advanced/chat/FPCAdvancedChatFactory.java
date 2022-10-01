package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.AdvancedChat;
import fpc.tools.advanced.chat.AdvancedChatFactory;
import fpc.tools.advanced.chat.MessageConverter;
import fpc.tools.advanced.chat.RequestAnswerMatcher;
import fpc.tools.chat.Chat;
import fpc.tools.lang.Instants;
import fpc.tools.lang.Priority;
import lombok.NonNull;

/**
 * @author Bastien Aracil
 **/
@Priority(Integer.MIN_VALUE)
public class FPCAdvancedChatFactory extends AdvancedChatFactory {

    @Override
    public @NonNull <M> AdvancedChat<M> createBasedOn(@NonNull Chat chat,
                                                      @NonNull RequestAnswerMatcher<M> matcher,
                                                      @NonNull MessageConverter<M> messageConverter,
                                                      @NonNull Instants instants) {
        return new FPCAdvancedChat<>(chat, matcher, messageConverter, instants);
    }

}
