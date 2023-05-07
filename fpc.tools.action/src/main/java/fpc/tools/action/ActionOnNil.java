package fpc.tools.action;

import fpc.tools.fp.Nil;

public abstract class ActionOnNil<R> extends BaseAction<Nil,R> {

    @Override
    public final R execute(Nil parameter) throws Throwable {
        return execute();
    }

    protected abstract R execute() throws Throwable;
}
