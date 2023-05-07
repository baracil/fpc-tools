package fpc.tools.chat;

import lombok.Getter;

/**
 * @author Bastien Aracil
 **/
public class MessagePostingFailure extends ChatException {

    @Getter
    private final String postMessage;

    public MessagePostingFailure(String postMessage, Throwable cause) {
        super("Could not post message '"+postMessage+"'", cause);
        this.postMessage = postMessage;
    }
}
