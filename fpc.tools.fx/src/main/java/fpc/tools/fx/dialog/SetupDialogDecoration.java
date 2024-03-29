package fpc.tools.fx.dialog;

import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SetupDialogDecoration implements DialogPreparer {

    @Override
    public <O> void setup(Stage dialogStage, DialogInfo<O> dialogInfo) {
        dialogInfo.resultProperty().addListener((l, o, n) -> {
            dialogInfo.updateDecoration(n.getValidationResult());
        });

        dialogStage.addEventHandler(WindowEvent.WINDOW_SHOWN,
                e -> dialogInfo.updateDecoration(dialogInfo.getDialogController().getDialogState().getValidationResult())
                );

    }

}
