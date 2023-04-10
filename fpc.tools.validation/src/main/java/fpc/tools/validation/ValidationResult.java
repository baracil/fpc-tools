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

    @NonNull
    private final Set<String> validatedFields;

    @NonNull
    private final Map<String, List<ValidationError>> errors;

    @NonNull
    public static ValidationResult empty() {
        return new ValidationResult(Set.of(), Map.of());
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    @NonNull
    public List<ValidationError> getErrors(@NonNull String fieldName) {
        return errors.getOrDefault(fieldName,List.of());
    }

    @NonNull
    public Optional<ValidationError> getFirstError(@NonNull String fieldName) {
        final List<ValidationError> fieldErrors = errors.get(fieldName);
        if (fieldErrors == null || fieldErrors.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(fieldErrors.get(0));
    }


}
