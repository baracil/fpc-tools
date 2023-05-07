package net.femtoparsec.tools.validation;

import fpc.tools.validation.ValidationContext;
import fpc.tools.validation.ValidationError;
import fpc.tools.validation.ValidationResult;
import lombok.NonNull;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FPCValidationContext implements ValidationContext  {

    private static final Collector<Map.Entry<String, Set<ValidationError>>, ?, Map<String, List<ValidationError>>> COLLECTOR
        = Collectors.toMap(Map.Entry::getKey, e -> List.copyOf(e.getValue()));

    private final Set<String> validatedFields = new HashSet<>();

    private final Map<String, Set<ValidationError>> errors = new HashMap<>();

    public void addValidatedField(String validatedField) {
        this.validatedFields.add(validatedField);
    }

    public void addError(ValidationError error) {
        this.errors.computeIfAbsent(error.getFieldName(), f -> new LinkedHashSet<>()).add(error);
    }

    public void addError(String fieldName, String errorType) {
        this.addError(new ValidationError(fieldName, errorType));
    }

    public ValidationResult getResult() {
        final Map<String, List<ValidationError>> errorByField = errors.entrySet()
                                                                                        .stream()
                                                                                        .collect(COLLECTOR);
        return new ValidationResult(Set.copyOf(validatedFields), errorByField);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public boolean isFieldValid(String fieldName) {
        return errors.get(fieldName) == null;
    }
}
