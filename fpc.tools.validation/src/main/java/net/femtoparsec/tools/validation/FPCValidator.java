package net.femtoparsec.tools.validation;

import fpc.tools.validation.ValidationContext;
import lombok.NonNull;

import javax.annotation.Nullable;

public class FPCValidator<O> extends AbstractValidator<O, FPCValidator<O>> {

    public FPCValidator(ValidationContext context, String fieldName, @Nullable O value) {
        super(context, fieldName, value);
    }

    @Override
    protected FPCValidator<O> getThis() {
        return this;
    }
}
