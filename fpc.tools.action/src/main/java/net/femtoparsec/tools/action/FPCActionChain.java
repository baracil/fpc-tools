package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionExecutor;
import fpc.tools.fp.Function2;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class FPCActionChain<P, U, R> extends ActionChainBase<P, R> {

    @Getter
    private final Class<? extends Action<?,?>> initialAction;

    private final Function2<? super ActionExecutor, ? super P, ? extends CompletionStage<U>> before;

    private final @NonNull Class<? extends Action<U, R>> then;

    @Override
    public @NonNull CompletionStage<R> launch(@NonNull ActionExecutor executor, @NonNull P parameter) {
        return before.apply(executor, parameter)
                     .thenCompose(r -> executor.pushAction(then, r));
    }

}
