package fpc.tools.validation;

import lombok.NonNull;

public interface ValidatorFactory<T,V extends fpc.tools.validation.Validator<T,V>> {

    @NonNull
    V create(@NonNull ValidationContext context, @NonNull String fieldName, T value);

}
