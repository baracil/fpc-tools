package fpc.tools.validation;

import lombok.NonNull;

import java.nio.file.Path;

public interface PathValidator extends fpc.tools.validation.Validator<Path,PathValidator> {

    @Override
    @NonNull PathValidator isNotNull();

    @NonNull
    PathValidator isAFolder();

    @NonNull
    PathValidator doesNotExist();

    @NonNull
    PathValidator isRegularFile();

    @NonNull
    PathValidator resolve(@NonNull StringValidator stringValidator);
}
