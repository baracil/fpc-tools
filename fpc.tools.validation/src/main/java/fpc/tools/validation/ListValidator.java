package fpc.tools.validation;

import fpc.tools.fp.Predicate1;
import lombok.NonNull;

import java.util.List;


public interface ListValidator<T> extends fpc.tools.validation.Validator<List<T>, ListValidator<T>> {

    @Override
    @NonNull ListValidator<T> isNotNull();

    @NonNull
    ListValidator<T> isNotEmpty();

    @Override
    @NonNull
    ListValidator<T> errorIfNot(@NonNull Predicate1<List<T>> test, @NonNull String errorType);
}
