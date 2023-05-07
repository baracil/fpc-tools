package net.femtoparsec.tools.validation;

import fpc.tools.validation.ErrorType;
import fpc.tools.validation.PathValidator;
import fpc.tools.validation.StringValidator;
import fpc.tools.validation.ValidationContext;
import lombok.NonNull;

import java.nio.file.Files;
import java.nio.file.Path;

public class FPCPathValidator extends AbstractValidator<Path, PathValidator> implements PathValidator {

    public FPCPathValidator(ValidationContext context,
                            String fieldName,
                            Path value) {
        super(context, fieldName, value);
    }

    @Override
    protected PathValidator getThis() {
        return this;
    }

    @Override
    public PathValidator isAFolder() {
        return errorIfNot(Files::isDirectory, ErrorType.A_FOLDER_IS_REQUIRED);
    }

    @Override
    public PathValidator doesNotExist() {
        return errorIfNot(Files::exists, ErrorType.PATH_SHOULD_NOT_EXIST);
    }

    @Override
    public PathValidator isRegularFile() {
        return errorIfNot(Files::isRegularFile, ErrorType.A_REGULAR_FILE_IS_REQUIRED);
    }

    @Override
    public PathValidator resolve(StringValidator stringValidator) {
        stringValidator.isNotBlank();
        return map(
                path -> path.resolve(stringValidator.getValue()),
                FPCPathValidator::new
        );
    }
}
