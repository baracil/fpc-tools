package net.femtoparsec.tools.validation;

import fpc.tools.validation.*;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

public class FPCValidation implements Validation {

    public static @NonNull ValidationFactory provider() {
        return FPCValidation::new;
    }

    private final ValidationContext context = new FPCValidationContext();

    @Override
    public @NonNull <T> Validator<T,?> with(@NonNull String fieldName, T value) {
        return new FPCValidator<>(context,fieldName,value);
    }

    @Override
    public @NonNull <T> Validator<T, ?> with(@NonNull String fieldName, Callable<T> getter) {
        try {
            return with(fieldName,getter.call());
        } catch (Exception e) {
            final FPCValidator<T> validator = new FPCValidator<>(context,fieldName,null);
            return validator.addError(ErrorType.INVALID_VALUE);
        }
    }

    @Override
    public @NonNull StringValidator with(@NonNull String fieldName, String value) {
        return new FPCStringValidator(context,fieldName, value);
    }

    @Override
    public @NonNull PathValidator with(@NonNull String fieldName, Path value) {
        return new net.femtoparsec.tools.validation.FPCPathValidator(context,fieldName,value);
    }

    @Override
    public @NonNull <U> ListValidator<U> with(@NonNull String fieldName, List<U> value) {
        return new FPCListValidator<>(context,fieldName,value);
    }

    @Override
    public boolean isValid() {
        return context.isValid();
    }

    @Override
    public @NonNull ValidationResult getResult() {
        return context.getResult();
    }
}
