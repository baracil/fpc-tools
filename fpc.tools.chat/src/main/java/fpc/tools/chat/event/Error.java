package fpc.tools.chat.event;

public record Error(Throwable error) implements ChatEvent {

    public static Error with(Throwable error) {
        return new Error(error);
    }

    @Override
    public void accept(ChatEventVisitor visitor) {
        visitor.visit(this);
    }

}
