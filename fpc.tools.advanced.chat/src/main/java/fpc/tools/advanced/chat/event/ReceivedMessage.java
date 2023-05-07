package fpc.tools.advanced.chat.event;

import lombok.Getter;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;

/**
 * Event sent when a message is received from the chat
 *
 * @param <M>
 */
public record ReceivedMessage<M>(@Getter Instant receptionTime,
                                 @Getter M message) implements AdvancedChatEvent<M> {

    @Override
    public <T> T accept(AdvancedChatEventVisitor<M, T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Optional<ReceivedMessage<M>> castToReceivedMessage() {
        return Optional.of(this);
    }

    @Override
    public String toString() {
        return "ReceivedMessage{" + message + "}";
    }
}
