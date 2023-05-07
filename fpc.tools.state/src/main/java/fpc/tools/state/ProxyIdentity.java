package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import fpc.tools.lang.Subscription;
import javafx.beans.value.ObservableValue;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ProxyIdentity<R> implements Identity<R> {

    private final Identity<R> delegate;

    public ProxyIdentity(Function1<? super IdentityMutator<R>, ? extends Identity<R>> factory) {
        this.delegate = factory.apply(this);
    }

    @Override
    public <V> CompletionStage<R> mutate(Mutation<V> subMutation, Accessor<R, V> accessor) {
        return delegate.mutate(subMutation,accessor);
    }

    @Override
    public <V> CompletionStage<V> mutate(Mutation<R> mutation, Function1<? super R, ? extends CompletionStage<V>> action) {
        return delegate.mutate(mutation,action);
    }

    @Override
    public <V> CompletionStage<V> mutate(Mutation<R> mutation, Function2<? super R, ? super R, ? extends CompletionStage<V>> action) {
        return delegate.mutate(mutation,action);
    }

    @Override
    public R getCurrentState() {
        return delegate.getCurrentState();
    }

    @Override
    public CompletionStage<R> mutate(Mutation<R> mutation) {
        return delegate.mutate(mutation);
    }

    @Override
    public Subscription addListener(IdentityListener<R> listener) {
        return delegate.addListener(listener);
    }

    @Override
    public void addWeakListener(IdentityListener<R> listener) {
        delegate.addWeakListener(listener);
    }

    @Override
    public <V> ObservableValue<V> asFXObservableValue(
            Function1<? super R, ? extends V> getter) {
        return delegate.asFXObservableValue(getter);
    }


}
