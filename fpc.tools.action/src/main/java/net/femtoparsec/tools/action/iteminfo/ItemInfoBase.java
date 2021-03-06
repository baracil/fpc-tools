package net.femtoparsec.tools.action.iteminfo;

import javafx.beans.value.ObservableValue;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.femtoparsec.tools.action.ItemInfo;

@RequiredArgsConstructor
public abstract class ItemInfoBase<I> implements ItemInfo {

    @NonNull
    private final I item;

    @Override
    public boolean isDisabled() {
        return isDisabled(item);
    }

    @Override
    public void bindAction(@NonNull Runnable executable) {
        bindAction(item,executable);
    }

    @Override
    public void unbindAction() {
        unbindAction(item);
    }

    @Override
    public void bindDisable(@NonNull ObservableValue<? extends Boolean> observableValue) {
        bindDisable(item,observableValue);
    }

    @Override
    public void unbindDisable() {
        unbindDisable(item);
    }

    protected abstract boolean isDisabled(@NonNull I item);

    protected abstract void bindDisable(@NonNull I item, @NonNull ObservableValue<? extends Boolean> observableValue);

    protected abstract void unbindDisable(@NonNull I item);


    protected abstract void bindAction(@NonNull I item, @NonNull Runnable executable);

    protected abstract void unbindAction(@NonNull I item);
}
