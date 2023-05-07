package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionChain;
import fpc.tools.action.ActionChainOpt;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class ActionChainBase<P, R> implements ActionChain<P, R> {

    @Override
    public <S> ActionChain<P, S> then(Class<? extends Action<R, S>> after) {
        return new FPCActionChain<>(getInitialAction(), this::launch,after);
    }

    @Override
    public <S> ActionChainOpt<P, S> thenOpt(Class<? extends Action<R, Optional<S>>> after) {
        return new FPCActionChainOpt<>(getInitialAction(), (e, p) -> launch(e,p).thenApply(Optional::of), after, s -> s);
    }
}
