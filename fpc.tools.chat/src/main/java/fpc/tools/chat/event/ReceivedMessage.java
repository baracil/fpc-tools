package fpc.tools.chat.event;

import lombok.Getter;

import java.time.Instant;

public record ReceivedMessage(@Getter Instant receptionTime,
                              @Getter String message) implements ChatEvent {

    @Override
    public void accept(ChatEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ReceivedMessage{'" + message + "'}";
    }
}
