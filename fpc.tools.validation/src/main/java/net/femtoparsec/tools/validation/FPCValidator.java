package net.femtoparsec.tools.validation;

import fpc.tools.validation.ValidationContext;
import lombok.NonNull;

public class FPCValidator<O> extends AbstractValidator<O, FPCValidator<O>> {

    public FPCValidator(@NonNull ValidationContext context, @NonNull String fieldName, O value) {
        super(context, fieldName, value);
    }

    @Override
    protected FPCValidator<O> getThis() {
        return this;
    }
}
