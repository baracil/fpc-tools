package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccessorFactory<S,B> {

    @NonNull
    private final Function1<? super S, ? extends B> toBuilder;

    @NonNull
    private final Function1<? super B, ? extends S> toState;

    @NonNull
    public <V> Accessor<S,V> create(
            @NonNull Function1<? super S, ? extends V> getter,
            @NonNull Function2<? super B, ? super V, ? extends B> setter
            ) {
        return Accessor.with(
                getter,
                (s,v) -> toState.f(setter.f(toBuilder.f(s),v))
        );
    }
}
