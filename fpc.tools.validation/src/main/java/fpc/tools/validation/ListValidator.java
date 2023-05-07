package fpc.tools.validation;

import fpc.tools.fp.Predicate1;

import java.util.List;


public interface ListValidator<T> extends fpc.tools.validation.Validator<List<T>, ListValidator<T>> {

    @Override
    ListValidator<T> isNotNull();

    ListValidator<T> isNotEmpty();

    @Override
    ListValidator<T> errorIfNot(Predicate1<List<T>> test, String errorType);
}
