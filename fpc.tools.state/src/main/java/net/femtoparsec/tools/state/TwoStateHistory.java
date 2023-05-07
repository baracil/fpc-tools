package net.femtoparsec.tools.state;

import fpc.tools.state.StateHistory;

public class TwoStateHistory<S> implements StateHistory<S> {

    private S previous;

    private S current;

    public TwoStateHistory(S initialState) {
        this.current = initialState;
    }

    @Override
    public boolean pushNewState(S state) {
        this.previous = this.current;
        this.current = state;
        return previous == null || !this.previous.equals(state);
    }

    @Override
    public S getCurrent() {
        return getAt(0);
    }

    @Override
    public S getPrevious() {
        return getAt(1);
    }

    @Override
    public S getAt(int depth) {
        final var value =  switch (depth) {
            case 0 -> current;
            case 1 -> previous;
            default -> null;
        };
        if (value == null) {
            throw new IndexOutOfBoundsException(depth);
        }
        return value;
    }

    @Override
    public int capacity() {
        return 2;
    }

    @Override
    public int size() {
        return ((previous == null)?0:1)+ ((current==null)?0:1);
    }
}
