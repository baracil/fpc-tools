package fpc.tools.action;

public interface Action<P,R> {

    R execute(P parameter) throws Throwable;

    default boolean isAsync() {
        return false;
    }

}
