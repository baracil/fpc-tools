package fpc.tools.chat;

/**
 * @author Bastien Aracil
 **/
public class ChatNotConnected extends ChatException {

    public ChatNotConnected() {
        super("The chat is not connected");
    }
}
