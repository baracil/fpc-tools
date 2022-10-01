package fpc.tools.chat;

import lombok.Getter;
import lombok.NonNull;

/**
 * @author Bastien Aracil
 **/
public class MessagePostingFailure extends ChatException {

    @NonNull
    @Getter
    private final String postMessage;

    public MessagePostingFailure(@NonNull String postMessage, @NonNull Throwable cause) {
        super("Could not post message '"+postMessage+"'", cause);
        this.postMessage = postMessage;
    }
}
