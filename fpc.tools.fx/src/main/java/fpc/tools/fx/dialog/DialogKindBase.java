package fpc.tools.fx.dialog;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Map;
import java.util.Optional;

public interface DialogKindBase<K extends DialogKindBase<K>> {

    Optional<Stage> getOwnerStage(Map<K, Stage> unmodifiableDialogStages);

    K getThis();

    Modality getModality();

    StageStyle getStageStyle();

    boolean isAlwaysOnTop();

}
