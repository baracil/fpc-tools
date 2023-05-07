package net.femtoparsec.tools.action.iteminfo;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.MenuItem;
import lombok.NonNull;

public class MenuItemBinder extends ItemInfoBase<MenuItem> {

    public MenuItemBinder(MenuItem item) {
        super(item);
    }

    @Override
    protected void bindDisable(MenuItem item, ObservableValue<? extends Boolean> observableValue) {
        item.disableProperty().bind(observableValue);
    }

    @Override
    protected void unbindDisable(MenuItem item) {
        item.disableProperty().unbind();
    }

    @Override
    protected void bindAction(MenuItem item, Runnable executable) {
        item.setOnAction(e -> executable.run());
    }

    @Override
    protected void unbindAction(MenuItem item) {
        item.setOnAction(e -> {});
    }

    @Override
    protected boolean isDisabled(MenuItem item) {
        return item.isDisable();
    }
}
