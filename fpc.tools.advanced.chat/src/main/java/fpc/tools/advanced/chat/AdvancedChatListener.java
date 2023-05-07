package fpc.tools.advanced.chat;

import fpc.tools.advanced.chat.event.AdvancedChatEvent;

/**
 * @author Bastien Aracil
 * @param <M> the type of message from the chat
 */
public interface AdvancedChatListener<M> {

    /**
     * Called when an event occurs on the chat
     * @param chatEvent the event that occurred
     */
    void onChatEvent(AdvancedChatEvent<M> chatEvent);
}
