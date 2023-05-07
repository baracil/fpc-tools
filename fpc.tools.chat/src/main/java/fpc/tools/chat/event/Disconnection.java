package fpc.tools.chat.event;

public record Disconnection() implements ChatEvent {

    private static final Disconnection DISCONNECTION = new Disconnection();

    public static Disconnection create() {
        return DISCONNECTION;
    }

    @Override
    public void accept(ChatEventVisitor visitor) {
        visitor.visit(this);
    }
}
