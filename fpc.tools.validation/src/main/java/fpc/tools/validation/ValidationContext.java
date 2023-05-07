package fpc.tools.validation;

public interface ValidationContext {


    void addValidatedField(String validatedField);

    void addError(ValidationError error);

    void addError(String fieldName, String errorType);

    ValidationResult getResult();

    boolean isValid();

    boolean isFieldValid(String fieldName);
}
