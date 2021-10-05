package net.femtoparsec.tools.action;

import fpc.tools.action.ActionExecutor;
import fpc.tools.action.AsyncAction;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class AsyncHead<P,R> extends ActionChainBase<P,R> {

    @Getter
    private final @NonNull Class<? extends AsyncAction<? super P, ? extends R>> initialAction;

    @Override
    public @NonNull CompletionStage<R> launch(@NonNull ActionExecutor executor, @NonNull P parameter) {
        return executor.pushAction(initialAction,parameter).thenCompose(f -> f.thenApply(r -> r));
    }

}
