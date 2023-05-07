package net.femtoparsec.tools.action;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public interface ItemInfo {

    void bindDisable(ObservableValue<? extends Boolean> observableValue);

    void unbindDisable();

    boolean isDisabled();

    void bindAction(Runnable executable);

    void unbindAction();

}
