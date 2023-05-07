package fpc.tools.action;

import lombok.NonNull;

public interface Action<P,R> {

    R execute(P parameter) throws Throwable;

    default boolean isAsync() {
        return false;
    }

}
