package fpc.tools.fx.dialog;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;
import net.femtoparsec.tools.fx.dialog.FADialogModel;

/**
 * @author Bastien Aracil
 */
public interface DialogModel<K extends DialogKindBase<K>> extends ReadOnlyDialogModel<K> {

    @NonNull
    static <K extends DialogKindBase<K>> DialogModel<K> create() {
        return new FADialogModel<>();
    }

    @Override
    @NonNull
    ObjectProperty<Scene> mainSceneProperty();

    default void setMainScene(Scene scene) {
        mainSceneProperty().set(scene);
    }

    @Override
    @NonNull
    ObjectProperty<Stage> primaryStageProperty();

    default void setPrimaryStage(Stage stage) {
        primaryStageProperty().set(stage);
    }

    @NonNull
    ObservableMap<K, Stage> getDialogStages();

    default void setDialogData(@NonNull K dialogKind, @NonNull Stage stage) {
        getDialogStages().put(dialogKind, stage);
    }

    default void clearDialogStage(@NonNull K dialogKind) {
        getDialogStages().put(dialogKind, null);
    }
}
