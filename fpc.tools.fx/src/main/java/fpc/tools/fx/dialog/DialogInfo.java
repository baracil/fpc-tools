package fpc.tools.fx.dialog;

import fpc.tools.validation.ValidationResult;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

@Builder(builderClassName = "Builder")
public class DialogInfo<O> {

    @Getter
    private final DialogController<?,O> dialogController;

    private final @Nullable Button cancelButton;

    private final @Nullable Button validateButton;

    private final @Nullable Button applyButton;

    @Singular
    private final Map<String, ControlInfo> validatableFields;

    public Optional<ControlInfo> getControl(String fieldName) {
        return Optional.ofNullable(validatableFields.get(fieldName));
    }

    public Optional<Button> getCancelButton() {
        return Optional.ofNullable(cancelButton);
    }

    public Optional<Button> getValidateButton() {
        return Optional.ofNullable(validateButton);
    }

    public Optional<Button> getApplyButton() {
        return Optional.ofNullable(applyButton);
    }

    public ObservableValue<DialogState<O>> resultProperty() {
        return dialogController.dialogStateProperty();
    }

    public void updateDecoration(ValidationResult validationResult) {
        for (String fieldName : validationResult.getValidatedFields()) {
            getControl(fieldName)
                      .ifPresent(control -> control.updateDecoration(validationResult.getErrors(fieldName)));
        }
    }

}
