package fpc.tools.action;

import fpc.tools.fp.TryResult;

public interface ActionSpy {

    <R, P> void onPushedAction(ActionExecutor actionExecutor, long id, Action<? super P, ? extends R> action, P parameter);

    <R> void onActionResult(ActionExecutor actionExecutor, long id, TryResult<Throwable, R> result);
}
