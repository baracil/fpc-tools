package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import lombok.NonNull;
import net.femtoparsec.tools.state.SimpleAccessor;

public interface Accessor<S, V> {

    V getValue(S state);

    S subMutation(S currentState, V newValue);


    static <S,V> Accessor<S,V> with(Function1<? super S, ? extends V> getter,
                                    Function2<? super S, ? super V, ? extends S> updater) {
        return new SimpleAccessor<>(getter,updater);
    }

    default fpc.tools.state.Mutation<S> wrap(fpc.tools.state.Mutation<V> subMutation) {
        return new SubMutation<S,V>(this,subMutation);
    }
}
