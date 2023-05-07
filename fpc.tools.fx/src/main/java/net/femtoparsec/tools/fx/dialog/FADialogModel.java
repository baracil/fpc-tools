package net.femtoparsec.tools.fx.dialog;

import fpc.tools.fx.dialog.DialogKindBase;
import fpc.tools.fx.dialog.DialogModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.NonNull;

/**
 * @author Bastien Aracil
 */
public class FADialogModel<K extends DialogKindBase<K>> implements DialogModel<K> {

    private final ObjectProperty<Scene> mainScene = new SimpleObjectProperty<>();
    private final ObjectProperty<Stage> primaryStage = new SimpleObjectProperty<>();
    private final ObservableMap<K, Stage> dialogStages = FXCollections.observableHashMap();
    private final ObservableMap<K, Stage> unmodifiableDialogStages = FXCollections.unmodifiableObservableMap(dialogStages);

    @Override
    public ObjectProperty<Scene> mainSceneProperty() {
        return mainScene;
    }

    @Override
    public ObjectProperty<Stage> primaryStageProperty() {
        return primaryStage;
    }

    @Override
    public ObservableMap<K, Stage> getDialogStages() {
        return this.dialogStages;
    }

    @Override
    public ObservableMap<K, Stage> getUnmodifiableDialogStages() {
        return unmodifiableDialogStages;
    }

}
