package fpc.tools.validation;

import lombok.NonNull;

public interface StringValidator extends fpc.tools.validation.Validator<String,StringValidator> {

    StringValidator isNotEmpty();

    StringValidator isNotBlank();

    @Override
    StringValidator isNotNull();

    fpc.tools.validation.PathValidator toPathValidator();

    fpc.tools.validation.PathValidator toPathValidator(fpc.tools.validation.PathValidator parent);
}
