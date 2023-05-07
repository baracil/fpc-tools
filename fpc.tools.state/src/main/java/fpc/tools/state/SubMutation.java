package fpc.tools.state;

import lombok.NonNull;

public class SubMutation<S, V> extends SubMutationBase<S,V> implements Mutation<S> {

    private final Mutation<V> subMutation;


    public SubMutation(Accessor<S, V> accessor, Mutation<V> subMutation) {
        super(accessor);
        this.subMutation = subMutation;
    }

    @Override
    protected V subMutate(V currentValue) {
        return subMutation.mutate(currentValue);
    }

}
