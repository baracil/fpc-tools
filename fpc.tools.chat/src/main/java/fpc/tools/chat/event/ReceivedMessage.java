package fpc.tools.chat.event;

import lombok.Getter;
import lombok.NonNull;

import java.time.Instant;

public record ReceivedMessage(@NonNull @Getter Instant receptionTime,
                              @NonNull @Getter String message) implements ChatEvent {

    @Override
    public void accept(@NonNull ChatEventVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ReceivedMessage{'" + message + "'}";
    }
}
