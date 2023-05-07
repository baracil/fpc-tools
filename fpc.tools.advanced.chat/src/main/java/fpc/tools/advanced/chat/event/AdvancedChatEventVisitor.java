package fpc.tools.advanced.chat.event;

import lombok.NonNull;

/**
 * Visitor for all {@link AdvancedChatEvent}
 * @param <M> the type of the message of the chat
 * @param <T> the type returned by the visitor
 */
public interface AdvancedChatEventVisitor<M,T> {

    T visit(Connection<M> event);

    T visit(Disconnection<M> event);

    T visit(PostedMessage<M> event);

    T visit(ReceivedMessage<M> event);

    T visit(Error<M> event);
}
