package net.femtoparsec.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import fpc.tools.state.Accessor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleAccessor<S,V> implements Accessor<S,V> {

    private final Function1<? super S, ? extends V> getter;

    private final Function2<? super S, ? super V, ? extends S> updater;

    @Override
    public V getValue(S state) {
        return getter.apply(state);
    }

    @Override
    public S subMutation(S currentState, V newValue) {
        return updater.apply(currentState,newValue);
    }
}
