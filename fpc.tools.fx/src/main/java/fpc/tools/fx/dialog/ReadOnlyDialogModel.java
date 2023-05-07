package fpc.tools.fx.dialog;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;

import java.util.Optional;

/**
 * @author Bastien Aracil
 */
public interface ReadOnlyDialogModel<K extends DialogKindBase<K>> {

    default Scene getMainScene() {
        return mainSceneProperty().get();
    }

    ReadOnlyObjectProperty<Scene> mainSceneProperty();

    default Stage getPrimaryStage() {
        return primaryStageProperty().get();
    }

    ReadOnlyObjectProperty<Stage> primaryStageProperty();


    ObservableMap<K, Stage> getUnmodifiableDialogStages();

    default Optional<Stage> getDialogStage(K dialogKind) {
        return Optional.ofNullable(getUnmodifiableDialogStages().get(dialogKind));
    }


}
