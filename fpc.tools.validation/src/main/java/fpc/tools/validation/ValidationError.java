package fpc.tools.validation;

import lombok.Value;

@Value
public class ValidationError {

    String fieldName;

    String errorType;
}
