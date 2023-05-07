package fpc.tools.fx.dialog;

import fpc.tools.validation.ValidationResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
public class DialogState<O> {

    public static <O> DialogState<O> empty() {
        return new DialogState<>(ValidationResult.empty(),null);
    }

    public static <O> DialogState<O> valid(O value) {
        return new DialogState<>(ValidationResult.empty(),value);
    }

    ValidationResult validationResult;

    @Getter(AccessLevel.NONE) @Nullable O value;

    public boolean isValid() {
        return value != null && validationResult.isValid();
    }

    public Optional<O> getValue() {
        if (isValid()) {
            return Optional.ofNullable(value);
        }
        return Optional.empty();
    }

}
