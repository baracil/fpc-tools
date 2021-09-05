package fpc.tools.action;

import com.google.common.collect.ImmutableList;
import fpc.tools.fp.TryResult;
import fpc.tools.lang.ListTool;
import lombok.NonNull;
import lombok.Synchronized;

public class ActionSpyDispatcher implements ActionSpy {

    private @NonNull ImmutableList<ActionSpy> spies = ImmutableList.of();

    @Synchronized
    public void addActionSpy(@NonNull ActionSpy actionSpy) {
        this.spies = ListTool.addFirst(actionSpy,spies);
    }

    public void removeActionSpy(@NonNull ActionSpy actionSpy) {
        this.spies = ListTool.removeOnceFrom(spies).apply(a -> a == actionSpy);
    }

    public <R, P> void onPushedAction(@NonNull ActionExecutor actionExecutor, long id, Action<? super P, ? extends R> action, @NonNull P parameter) {
        for (ActionSpy spy : spies) {
            try {
                spy.onPushedAction(actionExecutor,id,action,parameter);
            } catch (Throwable t) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(),t);
            }
        }
    }

    @Override
    public <R> void onActionResult(@NonNull ActionExecutor actionExecutor, long id, @NonNull TryResult<R, Throwable> result) {
        for (ActionSpy spy : spies) {
            try {
                spy.onActionResult(actionExecutor,id,result);
            } catch (Throwable t) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(),t);
            }
        }
    }
}
