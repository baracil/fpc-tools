package fpc.tools.lang;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class ValueHolderThreadLocal<T> implements ValueHolder<T> {

    private ThreadLocal<ValueHolder<T>> threadLocal;

    public ValueHolderThreadLocal(ThreadLocal<ValueHolder<T>> threadLocal) {
        this.threadLocal = threadLocal;
    }

    @Override
    public Optional<T> get() {
        return Optional.ofNullable(threadLocal.get()).flatMap(ValueHolder::get);
    }

    @Override
    public void push(T value) {
        final var holder = getOrCreateHolder();
        holder.push(value);
    }

    @Override
    public void pop() {
        final var holder = threadLocal.get();
        if (holder == null || holder.isEmpty()) {
            LOG.warn("Popping value from empty holder");
        } else {
            holder.pop();
        }
        if (holder != null && holder.isEmpty()) {
            threadLocal.remove();
        }
    }

    @Override
    public boolean isEmpty() {
        final var holder = threadLocal.get();
        return holder == null || holder.isEmpty();
    }

    private ValueHolder<T> getOrCreateHolder() {
        final var holder = threadLocal.get();
        if (holder != null) {
            return holder;
        }
        final var newHolder = new ValueHolderFifo<T>();
        threadLocal.set(newHolder);
        return newHolder;
    }
}
