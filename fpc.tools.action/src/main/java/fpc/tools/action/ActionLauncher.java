package fpc.tools.action;

import fpc.tools.fp.Nil;

/**
 * Launches an action from its type
 *  */
public interface ActionLauncher {

    /**
     * @param actionType the class of the action to execute
     * @param parameter the parameter to pass to the action
     * @param <P> the parameter type
     * @return a completion state that completes when the action is done
     */
    <P,R> ActionTicket<R> pushAction(Launchable<P,R> actionType, P parameter);


    /**
     * @param actionType the class of the action to execute
     * @param parameter the parameter to pass to the action
     * @param <P> the parameter type
     * @return a completion state that completes when the action is done
     */
    default <P,R> ActionTicket<R> pushAction(Class<? extends Action<P,R>> actionType, P parameter) {
        return pushAction(Launchable.single(actionType),parameter);
    }

    /**
     * short cut to <code>execute(actionType,Nil.NULL)</code>
     */
    default <R> ActionTicket<R> pushAction(Class<? extends Action<Nil,R>> actionType) {
        return pushAction(Launchable.single(actionType),Nil.NULL);
    }

    default <R> ActionTicket<R> pushActionAsync(Class<? extends AsyncAction<Nil,R>> actionType) {
        return pushAction(Launchable.singleAsync(actionType),Nil.NULL);
    }

    default <P,R> ActionTicket<R> pushActionAsync(Class<? extends AsyncAction<P,R>> actionType, P parameter) {
        return pushAction(Launchable.singleAsync(actionType),parameter);
    }

    /**
     * short cut to <code>execute(actionType,Nil.NULL)</code>
     */
    default <R> ActionTicket<R> pushAction(Launchable<Nil,R> actionType) {
        return pushAction(actionType,Nil.NULL);
    }

    void addActionSpy(ActionSpy actionSpy);

    void removeActionSpy(ActionSpy actionSpy);


}
