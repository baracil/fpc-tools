package fpc.tools.validation;

import lombok.NonNull;

public interface Validatable {

    @NonNull
    fpc.tools.validation.Validation validate(@NonNull fpc.tools.validation.Validation validation);
}
