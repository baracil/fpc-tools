package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.state.IdentityListener;
import javafx.application.Platform;
import javafx.beans.value.ObservableValueBase;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public class ObservableIdentityValue<R,S> extends ObservableValueBase<S> implements IdentityListener<R> {

    @NonNull
    private S value;

    private final AtomicReference<S> pending = new AtomicReference<>(null);

    @NonNull
    private final Function1<? super R, ? extends S> getter;

    public ObservableIdentityValue(@NonNull R initialValue, @NonNull Function1<? super R, ? extends S> getter) {
        this.getter = getter;
        this.value = getter.apply(initialValue);
    }

    @Override
    public S getValue() {
        return value;
    }

    @Override
    public void stateChanged(@NonNull R oldRoot, @NonNull R newRoot) {
        final S oldValue = getter.apply(oldRoot);
        final S newValue = getter.apply(newRoot);
        LOG.trace("StateChanged : {} -> {}", oldValue, newValue);
        if (oldValue == newValue) {
            return;
        }
        LOG.trace("WARN IN FX {}",newValue);
        final S pending = this.pending.getAndSet(newValue);
        if (pending == null) {
            LOG.trace("  --> DO WARN");
            Platform.runLater(this::update);
        } else {
            LOG.trace("  --> SKIP WARN");
        }
    }

    protected void update() {
        this.value = this.pending.getAndSet(null);
        LOG.warn("fireChangeEvent : "+value);
        fireValueChangedEvent();
    }
}
