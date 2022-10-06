package fpc.tools.action;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.TryResult;
import lombok.NonNull;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class WeakActionSpy implements ActionSpy {

    private final @NonNull Reference<ActionSpy> reference;

    public WeakActionSpy(ActionSpy reference) {
        this.reference = new WeakReference<>(reference);
    }

    @Override
    public <R, P> void onPushedAction(@NonNull ActionExecutor actionExecutor, long id, Action<? super P, ? extends R> action, @NonNull P parameter) {
        execute(actionExecutor, a -> a.onPushedAction(actionExecutor,id,action,parameter));
    }

    @Override
    public <R> void onActionResult(@NonNull ActionExecutor actionExecutor, long id, @NonNull TryResult<Throwable, R> result) {
        execute(actionExecutor, a -> a.onActionResult(actionExecutor,id,result));
    }

    private void execute(@NonNull ActionExecutor actionExecutor, @NonNull Consumer1<ActionSpy> action) {
        final ActionSpy actionSpy = reference.get();
        if (actionSpy == null) {
            actionExecutor.removeActionSpy(this);
        } else {
            action.accept(actionSpy);
        }
    }
}
