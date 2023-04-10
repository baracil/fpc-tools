package net.femtoparsec.tools.lang;

import fpc.tools.fp.Consumer1;
import fpc.tools.lang.ListTool;
import fpc.tools.lang.Listeners;
import fpc.tools.lang.ListenersFactory;
import fpc.tools.lang.Subscription;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FPCListeners<L> implements Listeners<L> {

    public static @NonNull ListenersFactory provider() {
        return new Factory();
    }

    @NonNull
    private List<L> listeners;

    public FPCListeners() {
        this(List.of());
    }

    public FPCListeners(@NonNull List<L> listeners) {
        this.listeners = listeners;
    }

    @Override
    public boolean isEmpty() {
        return listeners.isEmpty();
    }

    @NonNull
    @Synchronized
    public Subscription addListener(@NonNull L listener) {
        listeners = ListTool.addFirst(listener,listeners);
        return () -> remove(listener);
    }

    @Synchronized
    private void remove(@NonNull L listener) {
        listeners = ListTool.removeOnceFrom(listeners).apply(l -> l == listener);
    }

    public void forEachListeners(@NonNull Consumer1<? super L> action) {
        listeners.forEach(l -> forOneListener(l,action));
    }

    private void forOneListener(@NonNull L listener, @NonNull Consumer1<? super L> action) {
        action.acceptSafe(listener)
              .ifFailedAccept(e -> LOG.warn("Error while calling listener : ",e));
    }

    private static class Factory implements ListenersFactory {
        @Override
        public @NonNull <L> Listeners<L> create() {
            return new FPCListeners<>();
        }

        @Override
        public @NonNull <L> Listeners<L> create(@NonNull List<L> initialListeners) {
            return new FPCListeners<>(initialListeners);
        }
    }
}
