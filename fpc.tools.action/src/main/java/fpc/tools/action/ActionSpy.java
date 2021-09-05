package fpc.tools.action;

import fpc.tools.fp.TryResult;
import lombok.NonNull;

public interface ActionSpy {

    <R, P> void onPushedAction(@NonNull ActionExecutor actionExecutor, long id, Action<? super P, ? extends R> action, @NonNull P parameter);

    <R> void onActionResult(@NonNull ActionExecutor actionExecutor, long id, @NonNull TryResult<R, Throwable> result);
}
