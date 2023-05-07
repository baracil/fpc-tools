package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionChain;
import fpc.tools.action.ActionChainOpt;
import fpc.tools.fp.Nil;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class ActionChainOptBase<P, R> implements ActionChainOpt<P, R> {


    @Override
    public <S> ActionChainOpt<P, S> then(Class<? extends Action<R, S>> actionType) {
        return new FPCActionChainOpt<>(getInitialAction(), this::launch, actionType, Optional::of);
    }

    @Override
    public <S> ActionChainOpt<P, S> thenFlat(Class<? extends Action<R, Optional<S>>> actionType) {
        return new FPCActionChainOpt<>(getInitialAction(), this::launch, actionType, s -> s);
    }

    @Override
    public <S> ActionChain<P, S> thenOr(Class<? extends Action<R, S>> actionType,
                                                 Class<? extends Action<Nil, S>> or) {
        return new FPCActionChainOpt2<>(getInitialAction(), this::launch, actionType, or);
    }
}
