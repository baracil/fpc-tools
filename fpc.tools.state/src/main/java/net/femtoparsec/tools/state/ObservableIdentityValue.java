package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.state.IdentityListener;
import javafx.application.Platform;
import javafx.beans.value.ObservableValueBase;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class ObservableIdentityValue<R,S> extends ObservableValueBase<S> implements IdentityListener<R> {

    private S value;

    private final AtomicReference<S> pending = new AtomicReference<>(null);

    private final Function1<? super R, ? extends S> getter;

    public ObservableIdentityValue(R initialValue, Function1<? super R, ? extends S> getter) {
        this.getter = getter;
        this.value = getter.apply(initialValue);
    }

    @Override
    public S getValue() {
        return value;
    }

    @Override
    public void stateChanged(R oldRoot, R newRoot) {
        final S oldValue = getter.apply(oldRoot);
        final S newValue = getter.apply(newRoot);
        LOG.warn("StateChanged : {} -> {}", oldValue, newValue);
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
