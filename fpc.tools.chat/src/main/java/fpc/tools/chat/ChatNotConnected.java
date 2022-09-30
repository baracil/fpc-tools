package fpc.tools.chat;

/**
 * @author perococco
 **/
public class ChatNotConnected extends ChatException {

    public ChatNotConnected() {
        super("The chat is not connected");
    }
}
