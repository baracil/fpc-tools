package fpc.tools.validation;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public class ValidationResult {

    private final Set<String> validatedFields;

    private final Map<String, List<ValidationError>> errors;

    public static ValidationResult empty() {
        return new ValidationResult(Set.of(), Map.of());
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<ValidationError> getErrors(String fieldName) {
        return errors.getOrDefault(fieldName,List.of());
    }

    public Optional<ValidationError> getFirstError(String fieldName) {
        final List<ValidationError> fieldErrors = errors.get(fieldName);
        if (fieldErrors == null || fieldErrors.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(fieldErrors.get(0));
    }


}
