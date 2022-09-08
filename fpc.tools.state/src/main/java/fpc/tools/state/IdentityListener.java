package fpc.tools.state;

import fpc.tools.fp.Function1;
import lombok.NonNull;

public interface IdentityListener<R> {

    void stateChanged(@NonNull R oldValue, @NonNull R newValue);


    static <R,T> @NonNull IdentityListener<T> wrap(@NonNull IdentityListener<R> listener, Function1<? super T, ? extends R> mapper) {
        return (oldValue, newValue) -> listener.stateChanged(mapper.apply(oldValue), mapper.apply(newValue));
    }
}
