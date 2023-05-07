package fpc.tools.fx.dialog;

import javafx.stage.Stage;
import lombok.NonNull;

public class DefaultDialogPreparer implements DialogPreparer {

    private final SetupDialogDecoration setupDialogDecoration = new SetupDialogDecoration();
    private final SetupDialogButton setupDialogButton = new SetupDialogButton();

    @Override
    public <O> void setup(Stage dialogStage,
                          DialogInfo<O> dialogInfo) {
        setupDialogButton.setup(dialogStage, dialogInfo);
        setupDialogDecoration.setup(dialogStage, dialogInfo);
    }

}
