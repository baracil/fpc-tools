package fpc.tools.action;

import fpc.tools.fp.Nil;
import lombok.NonNull;

import java.util.Optional;

public interface ActionChainOpt<P,R> extends Launchable<P,Optional<R>> {

    <S> ActionChainOpt<P,S> then(Class<? extends Action<R,S>> actionType);

    <S> ActionChainOpt<P,S> thenFlat(Class<? extends Action<R,Optional<S>>> actionType);

    <S> ActionChain<P,S> thenOr(Class<? extends Action<R,S>> actionType, Class<? extends Action<Nil,S>> or);






}
