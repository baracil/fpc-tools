package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionExecutor;
import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class FPCActionChainOpt<P, U, R, RR> extends ActionChainOptBase<P,R> {

    @Getter
    private final Class<? extends Action<?,?>> initialAction;

    private final @NonNull Function2<? super ActionExecutor, ? super P, ? extends CompletionStage<Optional<U>>> before;

    private final @NonNull Class<? extends Action<U, RR>> then;

    private final @NonNull Function1<? super RR, ? extends Optional<R>> finalizer;


    @Override
    public @NonNull CompletionStage<Optional<R>> launch(@NonNull ActionExecutor executor, @NonNull P parameter) {
        return before.f(executor, parameter)
                     .thenCompose(u -> {
                         if (u.isPresent()) {
                             return executor.pushAction(then, u.get()).thenApply(finalizer);
                         } else {
                             return CompletableFuture.completedFuture(Optional.empty());
                         }
                     });
    }

}
