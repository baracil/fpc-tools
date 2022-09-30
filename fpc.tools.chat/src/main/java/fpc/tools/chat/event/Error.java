package fpc.tools.chat.event;

import lombok.NonNull;

public record Error(@NonNull Throwable error) implements ChatEvent {

    public static Error with(@NonNull Throwable error) {
        return new Error(error);
    }

    @Override
    public void accept(@NonNull ChatEventVisitor visitor) {
        visitor.visit(this);
    }

}
