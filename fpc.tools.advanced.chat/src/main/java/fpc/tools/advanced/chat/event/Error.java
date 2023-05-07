package fpc.tools.advanced.chat.event;

import lombok.NonNull;

public record Error<M>(Throwable error) implements AdvancedChatEvent<M> {

    public static <M> Error<M> create(Throwable error) {
        return new Error<>(error);
    }

    @Override
    public <T> T accept(AdvancedChatEventVisitor<M, T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Error{" +
                "error=" + error.getMessage() +
                '}';
    }
}
