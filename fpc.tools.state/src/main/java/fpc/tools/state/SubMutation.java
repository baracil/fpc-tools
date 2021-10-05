package fpc.tools.state;

import lombok.NonNull;

public class SubMutation<S, V> extends SubMutationBase<S,V> implements Mutation<S> {

    @NonNull
    private final Mutation<V> subMutation;


    public SubMutation(@NonNull Accessor<S, V> accessor, @NonNull Mutation<V> subMutation) {
        super(accessor);
        this.subMutation = subMutation;
    }

    @Override
    protected @NonNull V subMutate(@NonNull V currentValue) {
        return subMutation.mutate(currentValue);
    }

}
