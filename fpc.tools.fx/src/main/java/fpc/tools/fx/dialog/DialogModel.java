package fpc.tools.fx.dialog;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.femtoparsec.tools.fx.dialog.FADialogModel;

/**
 * @author Bastien Aracil
 */
public interface DialogModel<K extends DialogKindBase<K>> extends ReadOnlyDialogModel<K> {

    static <K extends DialogKindBase<K>> DialogModel<K> create() {
        return new FADialogModel<>();
    }

    @Override
    ObjectProperty<Scene> mainSceneProperty();

    default void setMainScene(Scene scene) {
        mainSceneProperty().set(scene);
    }

    @Override
    ObjectProperty<Stage> primaryStageProperty();

    default void setPrimaryStage(Stage stage) {
        primaryStageProperty().set(stage);
    }

    ObservableMap<K, Stage> getDialogStages();

    default void setDialogData(K dialogKind, Stage stage) {
        getDialogStages().put(dialogKind, stage);
    }

    default void clearDialogStage(K dialogKind) {
        getDialogStages().put(dialogKind, null);
    }
}
