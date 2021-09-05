package fpc.tools.validation;

import lombok.NonNull;

public interface StringValidator extends fpc.tools.validation.Validator<String,StringValidator> {

    @NonNull
    StringValidator isNotEmpty();

    @NonNull
    StringValidator isNotBlank();

    @Override
    @NonNull
    StringValidator isNotNull();

    @NonNull
    fpc.tools.validation.PathValidator toPathValidator();

    @NonNull
    fpc.tools.validation.PathValidator toPathValidator(@NonNull fpc.tools.validation.PathValidator parent);
}
