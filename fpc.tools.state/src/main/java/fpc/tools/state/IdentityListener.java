package fpc.tools.state;

import fpc.tools.fp.Function1;

public interface IdentityListener<R> {

    void stateChanged(R oldValue, R newValue);


    static <R,T> IdentityListener<T> wrap(IdentityListener<R> listener, Function1<? super T, ? extends R> mapper) {
        return (oldValue, newValue) -> listener.stateChanged(mapper.apply(oldValue), mapper.apply(newValue));
    }
}
