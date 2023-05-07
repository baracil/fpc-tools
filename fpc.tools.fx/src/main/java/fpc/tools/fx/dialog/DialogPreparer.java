package fpc.tools.fx.dialog;

import javafx.stage.Stage;

public interface DialogPreparer {

    <O> void setup(Stage dialogStage,
                   DialogInfo<O> dialogInfo);
}
