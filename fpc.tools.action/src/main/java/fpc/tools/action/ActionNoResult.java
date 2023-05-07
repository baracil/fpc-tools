package fpc.tools.action;

import fpc.tools.fp.Nil;

public abstract class ActionNoResult<P> extends BaseAction<P, Nil> {


    @Override
    public final Nil execute(P parameter) throws Throwable {
        doExecute(parameter);
        return Nil.NULL;
    }

    protected abstract void doExecute(P parameter) throws Throwable;
}
