package fpc.tools.fxmodel;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public abstract class ProxySelection<T,R extends Selection<T>> implements Selection<T> {

    @NonNull
    private final Selection<T> delegate;

    @Override
    public Optional<T> getMainSelection() {
        return delegate.getMainSelection();
    }

    @Override
    public @NonNull Set<T> getSelectedElements() {
        return delegate.getSelectedElements();
    }

    @Override
    public @NonNull R removeFromSelection(@NonNull T item) {
        final Selection<T> newSelection = delegate.removeFromSelection(item);
        if (newSelection == delegate) {
            return getThis();
        }
        return withNewSelection(newSelection);
    }

    @Override
    public @NonNull Selection<T> addToSelection(@NonNull T item) {
        final Selection<T> newSelection = delegate.addToSelection(item);
        if (newSelection == delegate) {
            return getThis();
        }
        return withNewSelection(newSelection);
    }

    protected abstract R withNewSelection(@NonNull Selection<T> selection);

    protected abstract R getThis();

    @Override
    public boolean isSelected(@NonNull T item) {
        return delegate.isSelected(item);
    }
}
