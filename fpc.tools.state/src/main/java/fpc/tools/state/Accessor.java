package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import lombok.NonNull;
import net.femtoparsec.tools.state.SimpleAccessor;

public interface Accessor<S, V> {

    @NonNull
    V getValue(@NonNull S state);

    @NonNull
    S subMutation(@NonNull S currentState, @NonNull V newValue);


    static <S,V> Accessor<S,V> with(@NonNull Function1<? super S, ? extends V> getter,
                                    @NonNull Function2<? super S, ? super V, ? extends S> updater) {
        return new SimpleAccessor<>(getter,updater);
    }

    @NonNull
    default fpc.tools.state.Mutation<S> wrap(@NonNull fpc.tools.state.Mutation<V> subMutation) {
        return new SubMutation<>(subMutation,this);
    }
}
