package fpc.tools.chat.event;

import lombok.NonNull;

public record Disconnection() implements ChatEvent {

    private static final Disconnection DISCONNECTION = new Disconnection();

    @NonNull
    public static Disconnection create() {
        return DISCONNECTION;
    }

    @Override
    public void accept(@NonNull ChatEventVisitor visitor) {
        visitor.visit(this);
    }
}
