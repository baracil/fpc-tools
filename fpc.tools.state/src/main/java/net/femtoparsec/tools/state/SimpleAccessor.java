package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import fpc.tools.state.Accessor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleAccessor<S,V> implements Accessor<S,V> {

    @NonNull
    private final Function1<? super S, ? extends V> getter;

    @NonNull
    private final Function2<? super S, ? super V, ? extends S> updater;

    @NonNull
    @Override
    public V getValue(@NonNull S state) {
        return getter.apply(state);
    }

    @NonNull
    @Override
    public S subMutation(@NonNull S currentState, @NonNull V newValue) {
        return updater.apply(currentState,newValue);
    }
}
