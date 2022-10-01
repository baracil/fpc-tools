package fpc.tools.chat;

import lombok.NonNull;

/**
 * Exception thrown when an error occurred during the chat connection
 * @author Bastien Aracil
 **/
public class ChatConnectionFailure extends ChatException {

    public ChatConnectionFailure(@NonNull String message, @NonNull Throwable cause) {
        super(message, cause);
    }
}
