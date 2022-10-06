package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface IdentityMutator<S> {

    /**
     * @param mutation the mutation to apply to the state
     * @return a completion stage contained the mutated state
     */
    @NonNull CompletionStage<S> mutate(@NonNull Mutation<S> mutation);

    @NonNull <V> CompletionStage<V> mutate(@NonNull Mutation<S> mutation, @NonNull Function1<? super S, ? extends CompletionStage<V>> action);

    @NonNull <V> CompletionStage<V> mutate(@NonNull Mutation<S> mutation, @NonNull Function2<? super S, ? super S, ? extends CompletionStage<V>> action);

    /**
     * Perform a sub mutation (a mutation of one of the property of the application state)
     * @param subMutation the mutation to apply to the property
     * @param accessor an accessor used to get/set the property from/to the applications state
     * @param <V> the type of the value to mutate
     * @return a completion stage contained the mutated application state
     */
    @NonNull
    default <V> CompletionStage<S> mutate(@NonNull Mutation<V> subMutation, @NonNull Accessor<S, V> accessor) {
        return mutate(new SubMutation<>(accessor,subMutation));
    }

}
