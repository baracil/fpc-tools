package fpc.tools.action;

import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface ActionExecutor {

    <P, R> CompletionStage<R> pushAction(Class<? extends Action<? super P, ? extends R>> action, P parameter);

    <P, R> CompletionStage<R> pushAction(Action<? super P, ? extends R> action, P parameter);

    void addActionSpy(ActionSpy actionSpy);

    void removeActionSpy(ActionSpy actionSpy);

}
