package fpc.tools.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class SubMutationBase<S, V> implements Mutation<S> {

    @NonNull
    private final Accessor<S, V> accessor;

    @NonNull
    @Override
    public S mutate(@NonNull S currentState) {
        final V currentValue = accessor.getValue(currentState);
        final V newValue = subMutate(currentValue);
        if (newValue == currentValue) {
            return currentState;
        }

        return accessor.subMutation(currentState,newValue);
    }

    protected abstract @NonNull V subMutate(@NonNull V currentValue);
}
