package fpc.tools.validation;

import lombok.NonNull;

public interface ValidationContext {


    void addValidatedField(@NonNull String validatedField);

    void addError(@NonNull ValidationError error);

    void addError(@NonNull String fieldName, @NonNull String errorType);

    @NonNull
    ValidationResult getResult();

    boolean isValid();

    boolean isFieldValid(String fieldName);
}
