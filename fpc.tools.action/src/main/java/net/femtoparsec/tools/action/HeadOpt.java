package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionExecutor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class HeadOpt<P,R> extends ActionChainOptBase<P, R> {

    @Getter
    private final @NonNull Class<? extends Action<P, Optional<R>>> initialAction;

    @Override
    public @NonNull CompletionStage<Optional<R>> launch(@NonNull ActionExecutor executor, @NonNull P parameter) {
        return executor.pushAction(initialAction,parameter);
    }



}
