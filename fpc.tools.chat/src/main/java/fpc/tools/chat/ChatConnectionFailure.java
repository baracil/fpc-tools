package fpc.tools.chat;

import lombok.NonNull;

/**
 * Exception thrown when an error occurred during the chat connection
 * @author Bastien Aracil
 **/
public class ChatConnectionFailure extends ChatException {

    public ChatConnectionFailure(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatConnectionFailure(String message) {
        super(message);
    }
}
