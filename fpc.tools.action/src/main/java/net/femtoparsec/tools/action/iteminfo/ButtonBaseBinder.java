package net.femtoparsec.tools.action.iteminfo;

import javafx.scene.control.ButtonBase;
import lombok.NonNull;

public class ButtonBaseBinder extends NodeBaseItemInfo<ButtonBase> {

    public ButtonBaseBinder(@NonNull ButtonBase item) {
        super(item);
    }

    @Override
    protected void bindAction(@NonNull ButtonBase item, @NonNull Runnable executable) {
        item.setOnAction(e -> executable.run());
    }

    @Override
    protected void unbindAction(@NonNull ButtonBase item) {
        item.setOnAction(null);
    }

}
