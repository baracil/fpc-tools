package fpc.tools.chat.event;

import lombok.NonNull;

public record Connection() implements ChatEvent {

    private static final Connection CONNECTION = new Connection();

    public static Connection create() {
        return CONNECTION;
    }

    @Override
    public void accept(ChatEventVisitor visitor) {
        visitor.visit(this);
    }
}
