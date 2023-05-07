package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionExecutor;
import fpc.tools.fp.Function2;
import fpc.tools.fp.Nil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class FPCActionChainOpt2<P, U, R> extends ActionChainBase<P, R> {

    @Getter
    private final Class<? extends Action<?,?>> initialAction;

    private final Function2<? super ActionExecutor, ? super P, ? extends CompletionStage<Optional<U>>> before;

    private final Class<? extends Action<U, R>> ifPresent;
    private final Class<? extends Action<Nil, R>> ifAbsent;

    @Override
    public CompletionStage<R> launch(ActionExecutor executor, P parameter) {
        return before.apply(executor, parameter)
                     .thenCompose(ou -> ou.map(u -> executor.pushAction(ifPresent, u))
                                      .orElseGet(() -> executor.pushAction(ifAbsent, Nil.NULL)));
    }

}
