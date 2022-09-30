package fpc.tools.advanced.chat.event;

import lombok.NonNull;

/**
 * Event sent when the chat is disconnected
 * @param <M>
 */
public record Disconnection<M>() implements AdvancedChatEvent<M> {

    private static final Disconnection<?> DISCONNECTION = new Disconnection<>();

    @SuppressWarnings("unchecked")
    @NonNull
    public static <M> Disconnection<M> create() {
        return (Disconnection<M>)DISCONNECTION;
    }

    @NonNull
    @Override
    public <T> T accept(@NonNull AdvancedChatEventVisitor<M,T> visitor) {
        return visitor.visit(this);
    }
}