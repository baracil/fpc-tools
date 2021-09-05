package fpc.tools.action;

import fpc.tools.fp.Nil;
import lombok.NonNull;

import java.util.Optional;

public interface ActionChainOpt<P,R> extends Launchable<P,Optional<R>> {

    @NonNull
    <S> ActionChainOpt<P,S> then(@NonNull Class<? extends Action<R,S>> actionType);

    @NonNull
    <S> ActionChainOpt<P,S> thenFlat(@NonNull Class<? extends Action<R,Optional<S>>> actionType);

    @NonNull
    <S> ActionChain<P,S> thenOr(@NonNull Class<? extends Action<R,S>> actionType, @NonNull Class<? extends Action<Nil,S>> or);






}
