package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionExecutor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class HeadStageOpt<P, R> extends ActionChainOptBase<P, R> {

    @Getter
    private final Class<? extends Action<P, CompletionStage<Optional<R>>>> initialAction;

    @Override
    public CompletionStage<Optional<R>> launch(ActionExecutor executor, P parameter) {
        return executor.pushAction(initialAction, parameter)
                       .thenCompose(s -> s);
    }


}
