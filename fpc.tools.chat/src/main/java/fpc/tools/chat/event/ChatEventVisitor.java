package fpc.tools.chat.event;

import java.util.function.Consumer;

public interface ChatEventVisitor {

    default Consumer<ChatEvent> toFunction() {
        return e -> e.accept(this);
    }

    void visit(Connection event);

    void visit(Disconnection event);

    void visit(PostedMessage event);

    void visit(ReceivedMessage event);

    void visit(Error event);
}
