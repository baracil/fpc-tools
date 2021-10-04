package net.femtoparsec.tools.action;

import fpc.tools.action.*;
import fpc.tools.fp.Nil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Log4j2
public class FPCActionManager implements ActionManager {

    @NonNull
    private final ActionStateProvider actionStateProvider;

    @NonNull
    private final ActionExecutor actionExecutor;

    @Override
    public void addActionSpy(@NonNull ActionSpy actionSpy) {
        actionExecutor.addActionSpy(actionSpy);
    }

    @Override
    public void removeActionSpy(@NonNull ActionSpy actionSpy) {
        actionExecutor.removeActionSpy(actionSpy);
    }

    @Override
    public @NonNull <P,R> ActionBinder<P> binder(@NonNull Launchable<P, R> launchable) {
        return new FPCActionBinder<>(actionStateProvider,actionExecutor,launchable);
    }

    @Override
    public @NonNull <R> NilActionBinder nilBinder(@NonNull Launchable<Nil, R> launchable) {
        final ActionBinder<Nil> delegate = binder(launchable);
        return new FPCNilActionBinder(delegate);
    }

    @Override
    public @NonNull <P, R> ActionTicket<R> pushAction(@NonNull Launchable<P, R> launchable,
                                                      @NonNull P parameter) {
        final CompletionStage<R> completionStage = launchable.launch(actionExecutor, parameter);
        final ActionTicket<R> ticket = new FPCActionTicket<>(actionExecutor, completionStage);

        return ticket.whenComplete(tryResult -> tryResult.ifFailedAccept(t -> logError(launchable,parameter,t)));

    }

    private <P, R> void logError(@NonNull Launchable<P, R> launchable,
                                 @NonNull P parameter,
                                 @NonNull Throwable t) {
        LOG.error("An error occurred while executing Action {}", launchable.getInitialAction(), t);
    }

}
