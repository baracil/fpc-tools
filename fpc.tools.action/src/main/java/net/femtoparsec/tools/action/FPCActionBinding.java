package net.femtoparsec.tools.action;

import fpc.tools.action.ActionBinding;
import fpc.tools.fp.Consumer0;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FPCActionBinding implements ActionBinding {

    private final BooleanProperty filteredProperty = new SimpleBooleanProperty();

    private final ItemInfo itemInfo;

    private final Consumer0 executable;

    private final ObservableBooleanValue disabledProperty;

    @Override
    public BooleanProperty filteredProperty() {
        return filteredProperty;
    }

    @Override
    public void bind() {
        this.itemInfo.bindDisable(filteredProperty.or(disabledProperty));
        this.itemInfo.bindAction(this::executeAction);
    }

    private void executeAction() {
        if (this.itemInfo.isDisabled() || filteredProperty.get()) {
            return;
        }
        executable.f();
    }

    @Override
    public void unbind() {
        this.itemInfo.unbindAction();
        this.itemInfo.unbindDisable();
    }
}
