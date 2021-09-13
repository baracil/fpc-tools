package fpc.tools.fx.dialog;

import fpc.tools.validation.ValidationResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class DialogState<O> {

    public static <O> @NonNull DialogState<O> empty() {
        return new DialogState<>(ValidationResult.empty(),null);
    }

    public static <O> @NonNull DialogState<O> valid(@NonNull O value) {
        return new DialogState<>(ValidationResult.empty(),value);
    }

    @NonNull ValidationResult validationResult;

    @Getter(AccessLevel.NONE) O value;

    public boolean isValid() {
        return value != null && validationResult.isValid();
    }

    @NonNull
    public Optional<O> getValue() {
        if (isValid()) {
            return Optional.ofNullable(value);
        }
        return Optional.empty();
    }

}
