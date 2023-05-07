package fpc.tools.validation;

import java.nio.file.Path;

public interface PathValidator extends fpc.tools.validation.Validator<Path,PathValidator> {

    @Override
    PathValidator isNotNull();

    PathValidator isAFolder();

    PathValidator doesNotExist();

    PathValidator isRegularFile();

    PathValidator resolve(StringValidator stringValidator);
}
