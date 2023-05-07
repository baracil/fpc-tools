package fpc.tools.action;

import fpc.tools.fp.TryResult;

public interface ActionFilter {

    /**
     * Called before executing an action.
     * Do not execute the action in this method unless you know what you are doing
     *
     * @param action the action that will be executed
     * @return the provided action by default, or another action to use
     */
    default <P,R> Action<P,R> preProcessAction(Action<P,R> action, P parameter) {
        return action;
    }

    /**
     * Called after an action has been executed
     * Do not execute the action in this method unless you know what you are doing
     *
     * @param action the action that has been executed
     * @param result the result of the action
     */
    <P,R> void postProcessAction(Action<? super P, ? extends R> action, TryResult<Throwable, R> result);
}
