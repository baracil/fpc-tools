package fpc.tools.action;

import fpc.tools.fp.Function0;

import java.util.Optional;

public interface ActionBinder<P> {

    ActionBinding createBinding(Object item, Function0<? extends Optional<? extends P>> parameterSupplier);

    default ActionBinding createBinding(Object item, P parameter) {
        return createBinding(item,Function0.cons(Optional.of(parameter)));
    }

    default ActionBinding bind(Object item, Function0<? extends Optional<? extends P>> parameterSupplier) {
        final ActionBinding binding = createBinding(item,parameterSupplier);
        binding.bind();
        return binding;
    }

    default ActionBinding bind(Object item, P parameter) {
        return bind(item,Function0.cons(Optional.of(parameter)));
    }

}
