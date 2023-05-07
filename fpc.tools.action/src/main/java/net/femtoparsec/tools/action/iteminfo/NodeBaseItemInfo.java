package net.femtoparsec.tools.action.iteminfo;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import lombok.NonNull;

public abstract class NodeBaseItemInfo<N extends Node> extends ItemInfoBase<N> {

    public NodeBaseItemInfo(N item) {
        super(item);
    }

    @Override
    protected boolean isDisabled(N item) {
        return item.isDisabled();
    }

    @Override
    protected void bindDisable(N item, ObservableValue<? extends Boolean> observableValue) {
        item.disableProperty().bind(observableValue);
    }

    @Override
    protected void unbindDisable(N item) {
        item.disableProperty().unbind();
    }

}
