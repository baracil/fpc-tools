package fpc.tools.action;

import javafx.beans.binding.BooleanBinding;
import lombok.NonNull;

public interface ActionStateProvider {

    @NonNull BooleanBinding disabledBinding(@NonNull Class<? extends Action<?,?>> actionType);
}
