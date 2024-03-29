package fpc.tools.lang;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

public class ValueHolderFifo<T> implements ValueHolder<T> {

    private final Deque<T> values = new LinkedList<>();

    @Override
    public Optional<T> get() {
        return Optional.ofNullable(values.peekLast());
    }

    @Override
    public void push(T value) {
        values.addLast(value);
    }

    @Override
    public void pop() {
        values.removeLast();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }
}
