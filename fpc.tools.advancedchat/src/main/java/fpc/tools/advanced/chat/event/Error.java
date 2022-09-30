package fpc.tools.advanced.chat.event;

import lombok.NonNull;

public record Error<M>(@NonNull Throwable error) implements AdvancedChatEvent<M> {

    @NonNull
    public static <M> Error<M> create(@NonNull Throwable error) {
        return new Error<>(error);
    }

    @NonNull
    @Override
    public <T> T accept(@NonNull AdvancedChatEventVisitor<M, T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Error{" +
                "error=" + error.getMessage() +
                '}';
    }
}
