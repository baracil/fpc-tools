package net.femtoparsec.tools.validation;

import fpc.tools.validation.ErrorType;
import fpc.tools.validation.PathValidator;
import fpc.tools.validation.StringValidator;
import fpc.tools.validation.ValidationContext;
import lombok.NonNull;

import java.nio.file.Path;

public class FPCStringValidator extends AbstractValidator<String, StringValidator> implements StringValidator  {


    public FPCStringValidator(@NonNull ValidationContext context,
                              @NonNull String fieldName,
                              String value) {
        super(context, fieldName, value);
    }

    @Override
    protected StringValidator getThis() {
        return this;
    }

    @Override
    public @NonNull StringValidator isNotEmpty() {
        return errorIf(String::isEmpty, ErrorType.NOT_EMPTY_TEXT_REQUIRED);
    }

    @Override
    public @NonNull StringValidator isNotBlank() {
        return errorIf(String::isBlank, ErrorType.NOT_EMPTY_TEXT_REQUIRED);
    }

    @Override
    public @NonNull PathValidator toPathValidator() {
        return map(Path::of, FPCPathValidator::new);
    }

    @Override
    public @NonNull PathValidator toPathValidator(@NonNull PathValidator parent) {
        return map(v -> parent.isAFolder().getValue().resolve(v), FPCPathValidator::new);
    }
}
