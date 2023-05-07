package fpc.tools.chat.event;

public sealed interface ChatEvent permits Connection, Disconnection, Error, PostedMessage, ReceivedMessage {

    void accept(ChatEventVisitor visitor);

}
