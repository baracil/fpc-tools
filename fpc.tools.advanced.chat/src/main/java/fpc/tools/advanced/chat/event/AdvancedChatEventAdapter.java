package fpc.tools.advanced.chat.event;

import lombok.NonNull;

public abstract class AdvancedChatEventAdapter<M,T> implements AdvancedChatEventVisitor<M,T> {

    protected abstract T fallback(AdvancedChatEvent<M> event);

    @Override
    public T visit(Connection<M> event) {
        return fallback(event);
    }

    @Override
    public T visit(Disconnection<M> event) {
        return fallback(event);
    }

    @Override
    public T visit(PostedMessage<M> event) {
        return fallback(event);
    }

    @Override
    public T visit(ReceivedMessage<M> event) {
        return fallback(event);
    }

    @Override
    public T visit(Error<M> event) {
        return fallback(event);
    }
}
