package fpc.tools.fx.dialog;

import fpc.tools.lang.Subscription;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

public interface DialogController<I,O> extends DialogResultHandler<O> {

    /**
     * @return a string property that will be used to set the title of the dialog
     */
    ObservableStringValue titleProperty();

    /**
     * Called before the dialog is shown
     * @param input the data to use to initialize the dialog
     * @return a subscription that will be unsubscribed by the caller when the dialog is closed or
     * if an error occurred while trying to display the dialog
     */
    Subscription initializeDialog(I input);

    void beforeShowing(Stage holderStage, I input);

    /**
     * @return an observable containing the current result (valid or invalid) of the dialog
     */
    ObservableValue<DialogState<O>> dialogStateProperty();

    /**
     * @return an observable that is true if the value of the dialog is valid. This is generally
     * the valid state provided by {@link DialogState#isValid()} but it might be different
     * depending of the dialog (that provides an extra condition for instance like instrument state)
     */
    ObservableBooleanValue invalidPropertyProperty();

    /**
     * @return the current dialog state
     */
    default DialogState<O> getDialogState() {
        return dialogStateProperty().getValue();
    }

    /**
     * Called when the dialog is closed (with a cancel button or by closing the window).
     * If this method returns false, the closing will be cancelled.
     * @return true if the dialog can be closed
     */
    default boolean requestPermissionToClose() {
        return true;
    }

}
