package net.femtoparsec.tools.action.iteminfo;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.femtoparsec.tools.action.ItemInfo;

@RequiredArgsConstructor
public abstract class ItemInfoBase<I> implements ItemInfo {

    private final I item;

    @Override
    public boolean isDisabled() {
        return isDisabled(item);
    }

    @Override
    public void bindAction(Runnable executable) {
        bindAction(item,executable);
    }

    @Override
    public void unbindAction() {
        unbindAction(item);
    }

    @Override
    public void bindDisable(ObservableValue<? extends Boolean> observableValue) {
        bindDisable(item,observableValue);
    }

    @Override
    public void unbindDisable() {
        unbindDisable(item);
    }

    protected abstract boolean isDisabled(I item);

    protected abstract void bindDisable(I item, ObservableValue<? extends Boolean> observableValue);

    protected abstract void unbindDisable(I item);


    protected abstract void bindAction(I item, Runnable executable);

    protected abstract void unbindAction(I item);
}
