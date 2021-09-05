package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionExecutor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class HeadStage<P,R> extends ActionChainBase<P,R> {

    @Getter
    private final @NonNull Class<? extends Action<P, CompletionStage<R>>> initialAction;

    @Override
    public @NonNull CompletionStage<R> launch(@NonNull ActionExecutor executor, @NonNull P parameter) {
        return executor.pushAction(initialAction,parameter).thenCompose(s -> s);
    }

}
