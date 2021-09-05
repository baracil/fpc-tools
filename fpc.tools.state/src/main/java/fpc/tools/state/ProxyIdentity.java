package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.lang.Subscription;
import javafx.beans.value.ObservableValue;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class ProxyIdentity<R> implements Identity<R> {

    @NonNull
    private final Identity<R> delegate;

    public ProxyIdentity(@NonNull R initialValue) {
        this(Identity.create(initialValue));
    }

    public static <S> Identity<S> create(@NonNull S initialValue) {
        return Identity.create(initialValue);
    }

    @Override
    public @NonNull <V> CompletionStage<R> mutate(@NonNull Mutation<V> subMutation, @NonNull Accessor<R, V> accessor) {
        return delegate.mutate(subMutation,accessor);
    }

    @Override
    @NonNull
    public CompletionStage<R> mutate(@NonNull Mutation<R> mutation) {
        return delegate.mutate(mutation);
    }

    @Override
    @NonNull
    public Subscription addListener(@NonNull IdentityListener<R> listener) {
        return delegate.addListener(listener);
    }

    @Override
    public void addWeakListener(@NonNull IdentityListener<R> listener) {
        delegate.addWeakListener(listener);
    }

    @Override
    @NonNull
    public <V> ObservableValue<V> asFXObservableValue(
            @NonNull Function1<? super R, ? extends V> getter) {
        return delegate.asFXObservableValue(getter);
    }

}
