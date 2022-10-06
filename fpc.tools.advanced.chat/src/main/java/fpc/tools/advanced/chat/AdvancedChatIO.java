package fpc.tools.advanced.chat;

import fpc.tools.lang.Subscription;
import lombok.NonNull;

/**
 * @author Bastien Aracil
 **/
public interface AdvancedChatIO<M> extends AdvancedIO<M> {

    /**
     * Add a listener that will received all event from this {@link AdvancedChatIO}
     * @param listener a listener to add
     * @return a subscription that can be used to remove the listener
     */
    @NonNull
    Subscription addChatListener(@NonNull AdvancedChatListener<M> listener);

    boolean isRunning();

}
