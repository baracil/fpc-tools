package net.femtoparsec.tools.validation;

import fpc.tools.validation.*;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

public class FPCValidation implements Validation {

    public static ValidationFactory provider() {
        return FPCValidation::new;
    }

    private final ValidationContext context = new FPCValidationContext();

    @Override
    public <T> Validator<T,?> with(String fieldName, T value) {
        return new FPCValidator<>(context,fieldName,value);
    }

    @Override
    public <T> Validator<T, ?> with(String fieldName, Callable<T> getter) {
        try {
            return with(fieldName,getter.call());
        } catch (Exception e) {
            final FPCValidator<T> validator = new FPCValidator<>(context,fieldName,null);
            return validator.addError(ErrorType.INVALID_VALUE);
        }
    }

    @Override
    public StringValidator with(String fieldName, String value) {
        return new FPCStringValidator(context,fieldName, value);
    }

    @Override
    public PathValidator with(String fieldName, Path value) {
        return new net.femtoparsec.tools.validation.FPCPathValidator(context,fieldName,value);
    }

    @Override
    public <U> ListValidator<U> with(String fieldName, List<U> value) {
        return new FPCListValidator<>(context,fieldName,value);
    }

    @Override
    public boolean isValid() {
        return context.isValid();
    }

    @Override
    public ValidationResult getResult() {
        return context.getResult();
    }
}
