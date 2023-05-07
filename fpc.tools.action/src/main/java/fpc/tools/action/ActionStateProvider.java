package fpc.tools.action;

import javafx.beans.binding.BooleanBinding;
import lombok.NonNull;

public interface ActionStateProvider {

    BooleanBinding disabledBinding(Class<? extends Action<?,?>> actionType);
}
