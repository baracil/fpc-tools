package fpc.tools.advanced.chat;

import fpc.tools.lang.Subscription;

/**
 * @author Bastien Aracil
 **/
public interface AdvancedChatIO<M> extends AdvancedIO {

    /**
     * Add a listener that will received all event from this {@link AdvancedChatIO}
     * @param listener a listener to add
     * @return a subscription that can be used to remove the listener
     */
    Subscription addChatListener(AdvancedChatListener<M> listener);

    boolean isRunning();

}
