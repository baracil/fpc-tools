package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccessorFactory<S,B> {

    private final Function1<? super S, ? extends B> toBuilder;

    private final Function1<? super B, ? extends S> toState;

    public <V> Accessor<S,V> create(
            Function1<? super S, ? extends V> getter,
            Function2<? super B, ? super V, ? extends B> setter
            ) {
        return Accessor.with(
                getter,
                (s,v) -> toState.apply(setter.apply(toBuilder.apply(s),v))
        );
    }
}
