package fpc.tools.validation;

import lombok.NonNull;

public interface ValidatorFactory<T,V extends fpc.tools.validation.Validator<T,V>> {

    V create(ValidationContext context, String fieldName, T value);

}
