package fpc.tools.advanced.chat;

import fpc.tools.advanced.chat.event.AdvancedChatEvent;
import fpc.tools.lang.Listeners;
import fpc.tools.lang.Subscription;
import lombok.NonNull;

public class AdvancedChatListenerDispatch<M> implements AdvancedChatListener<M> {

    private final @NonNull Listeners<AdvancedChatListener<M>> listeners = Listeners.create();

    @Override
    public void onChatEvent(@NonNull AdvancedChatEvent<M> chatEvent) {
        listeners.forEachListeners(AdvancedChatListener::onChatEvent,chatEvent);
    }

    public @NonNull Subscription addListener(@NonNull AdvancedChatListener<M> listener) {
        return listeners.addListener(listener);
    }
}
