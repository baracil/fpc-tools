package fpc.tools.validation;

import lombok.NonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("unused")
public interface Validation {

    @NonNull
    static Validation create() {
        return ValidationFactory.getInstance().create();
    }

    @NonNull
    <T> Validator<T,?> with(@NonNull String fieldName, Callable<T> getter);

    @NonNull
    <T> Validator<T,?> with(@NonNull String fieldName, T value);

    @NonNull
    StringValidator with(@NonNull String fieldName, String value);

    @NonNull
    PathValidator with(@NonNull String fieldName, Path value);

    @NonNull
    <U> ListValidator<U> with(@NonNull String fieldName, List<U> value);


    boolean isValid();

    @NonNull
    ValidationResult getResult();


}
