package net.femtoparsec.tools.validation;

import fpc.tools.validation.ErrorType;
import fpc.tools.validation.PathValidator;
import fpc.tools.validation.StringValidator;
import fpc.tools.validation.ValidationContext;

import java.nio.file.Path;

public class FPCStringValidator extends AbstractValidator<String, StringValidator> implements StringValidator  {


    public FPCStringValidator(ValidationContext context,
                              String fieldName,
                              String value) {
        super(context, fieldName, value);
    }

    @Override
    protected StringValidator getThis() {
        return this;
    }

    @Override
    public StringValidator isNotEmpty() {
        return errorIf(String::isEmpty, ErrorType.NOT_EMPTY_TEXT_REQUIRED);
    }

    @Override
    public StringValidator isNotBlank() {
        return errorIf(String::isBlank, ErrorType.NOT_EMPTY_TEXT_REQUIRED);
    }

    @Override
    public PathValidator toPathValidator() {
        return map(Path::of, FPCPathValidator::new);
    }

    @Override
    public PathValidator toPathValidator(PathValidator parent) {
        return map(v -> parent.isAFolder().getValue().resolve(v), FPCPathValidator::new);
    }
}
