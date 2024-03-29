package fpc.tools.action;

import fpc.tools.fp.TryResult;
import fpc.tools.lang.ListTool;
import lombok.Synchronized;

import java.util.List;

public class ActionSpyDispatcher implements ActionSpy {

    private List<ActionSpy> spies = List.of();

    @Synchronized
    public void addActionSpy(ActionSpy actionSpy) {
        this.spies = ListTool.addFirst(actionSpy,spies);
    }

    public void removeActionSpy(ActionSpy actionSpy) {
        this.spies = ListTool.removeOnceFrom(spies).apply(a -> a == actionSpy);
    }

    public <R, P> void onPushedAction(ActionExecutor actionExecutor, long id, Action<? super P, ? extends R> action, P parameter) {
        for (ActionSpy spy : spies) {
            try {
                spy.onPushedAction(actionExecutor,id,action,parameter);
            } catch (Throwable t) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(),t);
            }
        }
    }

    @Override
    public <R> void onActionResult(ActionExecutor actionExecutor, long id, TryResult<Throwable, R> result) {
        for (ActionSpy spy : spies) {
            try {
                spy.onActionResult(actionExecutor,id,result);
            } catch (Throwable t) {
                Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(),t);
            }
        }
    }
}
