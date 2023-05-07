package fpc.tools.action;

import net.femtoparsec.tools.action.Head;

import java.util.Optional;

public interface ActionChain<P,R> extends Launchable<P,R> {

    static <P,R> ActionChain<P,R> head(Class<? extends Action<P,R>> actionType) {
        return new Head<>(actionType);
    }

    <S> ActionChain<P,S> then(Class<? extends Action<R,S>> after);

    <S> ActionChainOpt<P,S> thenOpt(Class<? extends Action<R, Optional<S>>> after);
}
