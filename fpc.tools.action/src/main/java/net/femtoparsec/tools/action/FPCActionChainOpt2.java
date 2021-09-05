package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionExecutor;
import fpc.tools.fp.Function2;
import fpc.tools.fp.Nil;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class FPCActionChainOpt2<P, U, R> extends ActionChainBase<P, R> {

    @Getter
    private final Class<? extends Action<?,?>> initialAction;

    private final @NonNull Function2<? super ActionExecutor, ? super P, ? extends CompletionStage<Optional<U>>> before;

    private final @NonNull Class<? extends Action<U, R>> ifPresent;
    private final @NonNull Class<? extends Action<Nil, R>> ifAbsent;

    @Override
    public @NonNull CompletionStage<R> launch(@NonNull ActionExecutor executor, @NonNull P parameter) {
        return before.f(executor, parameter)
                     .thenCompose(ou -> ou.map(u -> executor.pushAction(ifPresent, u))
                                      .orElseGet(() -> executor.pushAction(ifAbsent, Nil.NULL)));
    }

}
