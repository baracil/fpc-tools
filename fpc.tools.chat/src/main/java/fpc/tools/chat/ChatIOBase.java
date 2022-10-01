package fpc.tools.chat;

import fpc.tools.chat.event.ChatEvent;
import fpc.tools.lang.Listeners;
import fpc.tools.lang.Subscription;
import lombok.NonNull;

/**
 * Chat implementing the listener part of the {@link ChatIO} interface.
 * It provides a method to warn the listener that can be
 * used by class extending <code>this</code> :
 *
 * <ol>
 *     <li>{@link #warnListeners(fpc.tools.chat.event.ChatEvent)}</li>
 * </ol>
 *
 *
 * @author Bastien Aracil
 **/
public abstract class ChatIOBase implements ChatIO {

    private final Listeners<ChatListener> listeners = Listeners.create();

    @Override
    public @NonNull Subscription addChatListener(@NonNull ChatListener listener) {
        return listeners.addListener(listener);
    }

    protected void warnListeners(@NonNull ChatEvent event) {
        listeners.forEachListeners(ChatListener::onChatEvent, event);
    }

}
