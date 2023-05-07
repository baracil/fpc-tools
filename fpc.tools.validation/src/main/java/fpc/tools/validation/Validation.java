package fpc.tools.validation;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings("unused")
public interface Validation {

    static Validation create() {
        return ValidationFactory.getInstance().create();
    }

    <T> Validator<T,?> with(String fieldName, Callable<T> getter);

    <T> Validator<T,?> with(String fieldName, T value);

    StringValidator with(String fieldName, String value);

    PathValidator with(String fieldName, Path value);

    <U> ListValidator<U> with(String fieldName, List<U> value);


    boolean isValid();

    ValidationResult getResult();


}
