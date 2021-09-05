package fpc.tools.action;

import fpc.tools.fp.Nil;
import lombok.NonNull;

public abstract class ActionOnNil<R> extends BaseAction<Nil,R> {

    @Override
    public final R execute(@NonNull Nil parameter) throws Throwable {
        return execute();
    }

    protected abstract R execute() throws Throwable;
}
