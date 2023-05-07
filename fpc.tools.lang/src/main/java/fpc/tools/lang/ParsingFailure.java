package fpc.tools.lang;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class ParsingFailure extends FPCException {

    private final String value;

    private final Class<?> targetType;

    public ParsingFailure(String value, Class<?> targetType) {
        super("Could not parse '"+value+"' to type '"+targetType+"'");
        this.value = value;
        this.targetType = targetType;
    }

    public ParsingFailure(String value, Class<?> targetType, Throwable cause) {
        this(value,targetType);
        initCause(cause);
    }
}
