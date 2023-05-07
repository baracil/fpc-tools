package net.femtoparsec.tools.action.iteminfo;

import javafx.scene.control.ButtonBase;

public class ButtonBaseBinder extends NodeBaseItemInfo<ButtonBase> {

    public ButtonBaseBinder(ButtonBase item) {
        super(item);
    }

    @Override
    protected void bindAction(ButtonBase item, Runnable executable) {
        item.setOnAction(e -> executable.run());
    }

    @Override
    protected void unbindAction(ButtonBase item) {
        item.setOnAction(null);
    }

}
