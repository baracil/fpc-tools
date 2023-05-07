package net.femtoparsec.tools.state;

import fpc.tools.lang.Disposer;
import fpc.tools.lang.Subscription;
import fpc.tools.state.IdentityListener;
import fpc.tools.state.ReadOnlyIdentity;
import lombok.Getter;

import java.lang.ref.Reference;

public class WeakIdentityListener<R> implements IdentityListener<R> {

    private static final Disposer DISPOSER = new Disposer("WeakIdentityListener");

    private final Reference<IdentityListener<R>> delegate;

    @Getter
    private final Subscription subscription;

    public WeakIdentityListener(ReadOnlyIdentity<R> identity,
                                IdentityListener<R> delegate) {
        this.subscription = identity.addListener(this);
        this.delegate = DISPOSER.add(delegate,subscription::unsubscribe);
    }

    @Override
    public void stateChanged(R oldValue, R newValue) {
        final IdentityListener<R> listener = delegate.get();
        if (listener != null) {
            listener.stateChanged(oldValue,newValue);
        }
    }
}
