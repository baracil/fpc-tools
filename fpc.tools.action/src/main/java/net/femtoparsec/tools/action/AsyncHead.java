package net.femtoparsec.tools.action;

import fpc.tools.action.ActionExecutor;
import fpc.tools.action.AsyncAction;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class AsyncHead<P,R> extends ActionChainBase<P,R> {

    @Getter
    private final Class<? extends AsyncAction<? super P, ? extends R>> initialAction;

    @Override
    public CompletionStage<R> launch(ActionExecutor executor, P parameter) {
        return executor.pushAction(initialAction,parameter).thenCompose(f -> f.thenApply(r -> r));
    }

}
