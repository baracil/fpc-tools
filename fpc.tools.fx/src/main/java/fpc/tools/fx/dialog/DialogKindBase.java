package fpc.tools.fx.dialog;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;

public interface DialogKindBase<K extends DialogKindBase<K>> {

    @NonNull Optional<Stage> getOwnerStage(@NonNull Map<K, Stage> unmodifiableDialogStages);

    @NonNull K getThis();

    @NonNull Modality getModality();

    @NonNull StageStyle getStageStyle();

    boolean isAlwaysOnTop();

    boolean isShowAndWait();
}
