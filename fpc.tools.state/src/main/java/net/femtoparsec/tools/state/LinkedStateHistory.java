package net.femtoparsec.tools.state;

import fpc.tools.state.StateHistory;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;

@RequiredArgsConstructor
public class LinkedStateHistory<S> implements StateHistory<S> {

    private final LinkedList<S> deque = new LinkedList<>();

    private final int capacity;

    public LinkedStateHistory(int capacity, S initialState) {
        this.capacity = capacity;
        this.deque.addFirst(initialState);
    }

    @Override
    public boolean pushNewState(S state) {
        if (deque.size()==capacity) {
            deque.removeLast();
        }
        final S previous = deque.peek();
        deque.addFirst(state);
        return previous == null || !previous.equals(state);
    }

    @Override
    public S getCurrent() {
        return deque.getFirst();
    }

    @Override
    public S getPrevious() {
        return getAt(1);
    }

    @Override
    public S getAt(int depth) {
        if (depth == 0) {
            return deque.getFirst();
        }
        return deque.get(depth);
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int size() {
        return deque.size();
    }
}
