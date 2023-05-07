package fpc.tools.fx.dialog;

import fpc.tools.lang.Subscription;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ObservableBooleanValue;
import lombok.NonNull;

public abstract class AbstractDialogController<I,O> implements DialogController<I,O> {

    private final ObjectProperty<DialogState<O>> resultProperty = new SimpleObjectProperty<>(DialogState.empty());

    private final BooleanProperty extraInvalidCondition = new SimpleBooleanProperty(false);

    private final StringProperty titleProperty = new SimpleStringProperty("");

    private final ObservableBooleanValue invalidProperty;

    public AbstractDialogController() {
        invalidProperty = Bindings.createBooleanBinding(() -> !resultProperty.get().isValid(),resultProperty)
                                  .or(extraInvalidCondition);
    }

    @Override
    public Subscription initializeDialog(I input) {
        this.setTitle(this.getInitialTitleDialog());
        return Subscription.NONE;
    }

    protected abstract String getInitialTitleDialog();

    protected void setDialogState(DialogState<O> dialogState) {
        resultProperty.set(dialogState);
    }

    @Override
    public ObjectProperty<DialogState<O>> dialogStateProperty() {
        return resultProperty;
    }

    @Override
    public ObservableBooleanValue invalidPropertyProperty() {
        return invalidProperty;
    }


    public String getTitle() {
        return titleProperty.get();
    }

    public StringProperty titleProperty() {
        return titleProperty;
    }

    public void setTitle(String title) {
        this.titleProperty.set(title);
    }
}
