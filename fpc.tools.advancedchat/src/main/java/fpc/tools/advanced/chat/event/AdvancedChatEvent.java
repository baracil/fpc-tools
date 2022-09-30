package fpc.tools.advanced.chat.event;

import fpc.tools.advanced.chat.AdvancedChat;
import lombok.NonNull;

import java.util.Optional;

/**
 * An event from an {@link AdvancedChat}.
 *
 * @author Bastien Aracil
 */
public sealed interface AdvancedChatEvent<M> permits Error, Connection, Disconnection, PostedMessage, ReceivedMessage {

    @NonNull <T> T accept(@NonNull AdvancedChatEventVisitor<M, T> visitor);

    default @NonNull Optional<ReceivedMessage<M>> castToReceivedMessage() {
        return Optional.empty();
    }

}
