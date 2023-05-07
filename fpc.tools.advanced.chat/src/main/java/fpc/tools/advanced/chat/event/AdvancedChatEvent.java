package fpc.tools.advanced.chat.event;

import fpc.tools.advanced.chat.AdvancedChat;

import java.util.Optional;

/**
 * An event from an {@link AdvancedChat}.
 *
 * @author Bastien Aracil
 */
public sealed interface AdvancedChatEvent<M> permits Error, Connection, Disconnection, PostedMessage, ReceivedMessage {

    <T> T accept(AdvancedChatEventVisitor<M, T> visitor);

    default Optional<ReceivedMessage<M>> castToReceivedMessage() {
        return Optional.empty();
    }

}
