package fpc.tools.validation;

import lombok.NonNull;

public interface Validatable {

    fpc.tools.validation.Validation validate(fpc.tools.validation.Validation validation);
}
