package net.femtoparsec.tools.state;

import fpc.tools.lang.Disposer;
import fpc.tools.lang.Subscription;
import fpc.tools.state.IdentityListener;
import fpc.tools.state.ReadOnlyIdentity;
import lombok.Getter;
import lombok.NonNull;

import java.lang.ref.Reference;

public class WeakIdentityListener<R> implements IdentityListener<R> {

    private static final Disposer DISPOSER = new Disposer("WeakIdentityListener");

    @NonNull
    private final Reference<IdentityListener<R>> delegate;

    @NonNull
    @Getter
    private final Subscription subscription;

    public WeakIdentityListener(@NonNull ReadOnlyIdentity<R> identity,
                                @NonNull IdentityListener<R> delegate) {
        this.subscription = identity.addListener(this);
        this.delegate = DISPOSER.add(delegate,subscription::unsubscribe);
    }

    private IdentityListener<R> value() {
        return delegate.get();
    }

    @Override
    public void stateChanged(@NonNull R oldValue, @NonNull R newValue) {
        final IdentityListener<R> listener = delegate.get();
        if (listener != null) {
            listener.stateChanged(oldValue,newValue);
        }
    }
}
