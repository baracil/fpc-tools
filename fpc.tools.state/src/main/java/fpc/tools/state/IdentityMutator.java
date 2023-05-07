package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;

import java.util.concurrent.CompletionStage;

public interface IdentityMutator<S> {

    /**
     * @param mutation the mutation to apply to the state
     * @return a completion stage contained the mutated state
     */
    CompletionStage<S> mutate(Mutation<S> mutation);

    <V> CompletionStage<V> mutate(Mutation<S> mutation, Function1<? super S, ? extends CompletionStage<V>> action);

    <V> CompletionStage<V> mutate(Mutation<S> mutation, Function2<? super S, ? super S, ? extends CompletionStage<V>> action);

    /**
     * Perform a sub mutation (a mutation of one of the property of the application state)
     * @param subMutation the mutation to apply to the property
     * @param accessor an accessor used to get/set the property from/to the applications state
     * @param <V> the type of the value to mutate
     * @return a completion stage contained the mutated application state
     */
    default <V> CompletionStage<S> mutate(Mutation<V> subMutation, Accessor<S, V> accessor) {
        return mutate(new SubMutation<>(accessor,subMutation));
    }

}
