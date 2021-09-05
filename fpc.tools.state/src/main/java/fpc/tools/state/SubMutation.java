package fpc.tools.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubMutation<S, V> implements Mutation<S> {

    @NonNull
    private final Mutation<V> subMutation;

    @NonNull
    private final Accessor<S, V> accessor;

    @NonNull
    @Override
    public S mutate(@NonNull S currentState) {
        final V currentValue = accessor.getValue(currentState);
        final V newValue = subMutation.mutate(currentValue);
        if (newValue == currentValue) {
            return currentState;
        }

        return accessor.subMutation(currentState,newValue);
    }
}
