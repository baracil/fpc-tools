package net.femtoparsec.tools.action.iteminfo;

import javafx.beans.value.ObservableValue;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectBinder extends ItemInfoBase<Object> {

    public ObjectBinder(Object item) {
        super(item);
    }

    @Override
    public void bindDisable(Object item, ObservableValue<? extends Boolean> observableValue) {
        LOG.warn("Binding on an unknown item. No disable property available to bind. Item type : {}",item.getClass());
    }

    @Override
    protected void unbindDisable(Object item) {    }

    @Override
    protected void bindAction(Object item, Runnable executable) {
        LOG.warn("Binding on an unknown item. No action available to bind the executable. Item type : {}",item.getClass());
    }

    @Override
    protected void unbindAction(Object item) {}

    @Override
    protected boolean isDisabled(Object item) {
        return true;
    }
}
