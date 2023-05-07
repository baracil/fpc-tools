package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionExecutor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class Head<P,R> extends ActionChainBase<P,R> {

    @Getter
    private final Class<? extends Action<? super P, ? extends R>> initialAction;

    @Override
    public CompletionStage<R> launch(ActionExecutor executor, P parameter) {
        return executor.pushAction(initialAction,parameter);
    }

}
