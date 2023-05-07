package fpc.tools.state;

public interface Mutation<S> {

    /**
     * @param <S> the type of the state to mutate
     * @return a mutation that changes nothing
     */
    static <S> Mutation<S> identity() {
        return s -> s;
    }

    /**
     * perform this mutation
     * @param currentState the current State
     * @return the mutated state
     */
    S mutate(S currentState);

    /**
     * @param after the mutation to apply after this one
     * @return a new mutation that perform this and the provided one in a row
     */
    default Mutation<S> then(Mutation<S> after) {
        return s -> after.mutate(this.mutate(s));
    }
}
