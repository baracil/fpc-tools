package net.femtoparsec.tools.validation;

import fpc.tools.validation.ErrorType;
import fpc.tools.validation.ListValidator;
import fpc.tools.validation.ValidationContext;
import lombok.NonNull;

import java.util.List;


/**
 */
public class FPCListValidator<T> extends AbstractValidator<List<T>, ListValidator<T>> implements ListValidator<T> {

    public FPCListValidator(ValidationContext context, String fieldName, List<T> value) {
        super(context, fieldName, value);
    }


    @Override
    protected ListValidator<T> getThis() {
        return this;
    }

    @Override
    public ListValidator<T> isNotEmpty() {
        return errorIf(List::isEmpty, ErrorType.NOT_EMPTY_LIST_REQUIRED);
    }
}
