package fpc.tools.chat.event;

import lombok.NonNull;

public sealed interface ChatEvent permits Connection, Disconnection, Error, PostedMessage, ReceivedMessage {

    void accept(ChatEventVisitor visitor);

}
