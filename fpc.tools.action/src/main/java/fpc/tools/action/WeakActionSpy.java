package fpc.tools.action;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.TryResult;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class WeakActionSpy implements ActionSpy {

    private final Reference<ActionSpy> reference;

    public WeakActionSpy(ActionSpy reference) {
        this.reference = new WeakReference<>(reference);
    }

    @Override
    public <R, P> void onPushedAction(ActionExecutor actionExecutor, long id, Action<? super P, ? extends R> action, P parameter) {
        execute(actionExecutor, a -> a.onPushedAction(actionExecutor,id,action,parameter));
    }

    @Override
    public <R> void onActionResult(ActionExecutor actionExecutor, long id, TryResult<Throwable, R> result) {
        execute(actionExecutor, a -> a.onActionResult(actionExecutor,id,result));
    }

    private void execute(ActionExecutor actionExecutor, Consumer1<ActionSpy> action) {
        final ActionSpy actionSpy = reference.get();
        if (actionSpy == null) {
            actionExecutor.removeActionSpy(this);
        } else {
            action.accept(actionSpy);
        }
    }
}
