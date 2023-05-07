package fpc.tools.action;

import fpc.tools.fp.Nil;

/**
 *
 */
public interface ActionManager extends ActionLauncher {

    <P, R> ActionBinder<P> binder(Launchable<P, R> actionType);

    <R> NilActionBinder nilBinder(Launchable<Nil, R> actionType);

    default <P, R> ActionBinder<P> binder(Class<? extends Action<P, R>> actionType) {
        return binder(Launchable.single(actionType));
    }

    default <R> NilActionBinder nilBinder(Class<? extends Action<Nil, R>> actionType) {
        return nilBinder(Launchable.single(actionType));
    }

    default <R> ActionBinding createBinding(Class< ? extends Action<Nil,R>> actionType, Object item) {
        return nilBinder(actionType).createBinding(item);
    }

}
