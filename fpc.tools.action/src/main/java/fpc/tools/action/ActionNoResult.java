package fpc.tools.action;

import fpc.tools.fp.Nil;
import lombok.NonNull;

public abstract class ActionNoResult<P> extends BaseAction<P, Nil> {


    @Override
    public final Nil execute(@NonNull P parameter) throws Throwable {
        doExecute(parameter);
        return Nil.NULL;
    }

    protected abstract void doExecute(@NonNull P parameter) throws Throwable;
}
