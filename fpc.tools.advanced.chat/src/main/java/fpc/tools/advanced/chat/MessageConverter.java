package fpc.tools.advanced.chat;

import java.util.Optional;

/**
 * Convert a text message from the chat to
 * a typed message
 * @author Bastien Aracil
 * @param <M> the type of the message received from the chat
 **/
public interface MessageConverter<M> {

    /**
     * Convert the text message to the type of the messages of the chat
     * @param messageAsString the text message
     * @return an optional containing the converted message, an empty optional
     * if the conversion could not be done
     */
    Optional<M> convert(String messageAsString);
}
