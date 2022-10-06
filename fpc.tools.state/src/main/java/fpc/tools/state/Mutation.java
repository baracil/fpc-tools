package fpc.tools.state;

import lombok.NonNull;

public interface Mutation<S> {

    /**
     * @param <S> the type of the state to mutate
     * @return a mutation that changes nothing
     */
    @NonNull
    static <S> Mutation<S> identity() {
        return s -> s;
    }

    /**
     * perform this mutation
     * @param currentState the current State
     * @return the mutated state
     */
    @NonNull
    S mutate(@NonNull S currentState);

    /**
     * @param after the mutation to apply after this one
     * @return a new mutation that perform this and the provided one in a row
     */
    @NonNull
    default Mutation<S> then(@NonNull Mutation<S> after) {
        return s -> after.mutate(this.mutate(s));
    }
}
