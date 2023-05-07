package fpc.tools.action;

import lombok.NonNull;

public interface NilActionBinder {

    ActionBinding createBinding(Object item);

    default ActionBinding bind(Object item) {
        final ActionBinding binding = createBinding(item);
        binding.bind();
        return binding;
    }
}
