package fpc.tools.action;

import javafx.beans.binding.BooleanBinding;

public interface ActionStateProvider {

    BooleanBinding disabledBinding(Class<? extends Action<?,?>> actionType);
}
