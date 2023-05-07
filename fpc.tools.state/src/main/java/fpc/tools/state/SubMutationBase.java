package fpc.tools.state;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class SubMutationBase<S, V> implements Mutation<S> {

    private final Accessor<S, V> accessor;

    @Override
    public S mutate(S currentState) {
        final V currentValue = accessor.getValue(currentState);
        final V newValue = subMutate(currentValue);
        if (newValue == currentValue) {
            return currentState;
        }

        return accessor.subMutation(currentState,newValue);
    }

    protected abstract V subMutate(V currentValue);
}
