package fpc.tools.action;

import fpc.tools.fp.Nil;

public abstract class ActionOnNilNoResult extends BaseAction<Nil,Nil> {

    @Override
    public final Nil execute(Nil parameter) throws Throwable {
        execute();
        return Nil.NULL;
    }

    protected abstract void execute() throws Throwable;
}
