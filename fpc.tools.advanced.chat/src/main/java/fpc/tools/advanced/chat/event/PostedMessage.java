package fpc.tools.advanced.chat.event;

import fpc.tools.advanced.chat.Message;
import lombok.Getter;

import java.time.Instant;

/**
 * Event sent when a message is posted to the chat
 *
 * @param <M>
 */
public record PostedMessage<M>(Instant dispatchingTime,
                               @Getter Message postedMessage) implements AdvancedChatEvent<M> {

    @Override
    public <T> T accept(AdvancedChatEventVisitor<M, T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        final String payload = postedMessage.payload(Instant.EPOCH);
        final int idx = payload.indexOf("PASS");
        final String value;
        if (idx >= 0) {
            value = payload.substring(0, idx + 4) + " ******";
        } else {
            value = payload;
        }
        return "PostedMessage{" + value + "}";
    }

}
