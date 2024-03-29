package fpc.tools.chat.event;

import lombok.Getter;

public record PostedMessage(@Getter String postedMessage) implements ChatEvent {

    @Override
    public void accept(ChatEventVisitor visitor) {
        visitor.visit(this);
    }


    @Override
    public String toString() {
        final int idx = postedMessage.indexOf("PASS");
        final String value;
        if (idx >= 0) {
            value = postedMessage.substring(0, idx + 4) + " ******";
        } else {
            value = postedMessage;
        }
        return "PostedMessage{" + value + "}";
    }
}
