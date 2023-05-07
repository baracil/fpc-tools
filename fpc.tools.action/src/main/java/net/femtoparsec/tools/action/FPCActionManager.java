package net.femtoparsec.tools.action;

import fpc.tools.action.*;
import fpc.tools.fp.Nil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Slf4j
public class FPCActionManager implements ActionManager {

    private final ActionStateProvider actionStateProvider;

    private final ActionExecutor actionExecutor;

    @Override
    public void addActionSpy(ActionSpy actionSpy) {
        actionExecutor.addActionSpy(actionSpy);
    }

    @Override
    public void removeActionSpy(ActionSpy actionSpy) {
        actionExecutor.removeActionSpy(actionSpy);
    }

    @Override
    public <P,R> ActionBinder<P> binder(Launchable<P, R> launchable) {
        return new FPCActionBinder<>(actionStateProvider,actionExecutor,launchable);
    }

    @Override
    public <R> NilActionBinder nilBinder(Launchable<Nil, R> launchable) {
        final ActionBinder<Nil> delegate = binder(launchable);
        return new FPCNilActionBinder(delegate);
    }

    @Override
    public <P, R> ActionTicket<R> pushAction(Launchable<P, R> launchable,
                                                      P parameter) {
        final CompletionStage<R> completionStage = launchable.launch(actionExecutor, parameter);
        final ActionTicket<R> ticket = new FPCActionTicket<>(actionExecutor, completionStage);

        return ticket.whenComplete(tryResult -> tryResult.ifFailedAccept(t -> logError(launchable,parameter,t)));

    }

    private <P, R> void logError(Launchable<P, R> launchable,
                                 P parameter,
                                 Throwable t) {
        LOG.error("An error occurred while executing Action {}", launchable.getInitialAction(), t);
    }

}
