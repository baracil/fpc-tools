package net.femtoparsec.tools.action;

import fpc.tools.action.Action;
import fpc.tools.action.ActionExecutor;
import fpc.tools.action.ActionTicket;
import fpc.tools.action.AsyncAction;
import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Function1;
import fpc.tools.fp.Nil;
import fpc.tools.fp.TryResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class FPCActionTicket<R> implements ActionTicket<R> {

    private final ActionExecutor actionExecutor;

    @NonNull
    private final CompletionStage<R> completionStage;

    @Override
    public @NonNull ActionTicket<R> whenComplete(@NonNull Consumer1<? super TryResult<? super Throwable, ? super R>> action) {
        return withNewCompletionStage(completionStage.whenComplete((r,t) -> action.accept(t != null ? TryResult.failure(t):TryResult.success(r))));
    }

    @Override
    public @NonNull ActionTicket<Nil> thenAccept(@NonNull Consumer1<? super R> action) {
        return withNewCompletionStage(completionStage.thenAccept(action).thenApply(v -> Nil.NULL));
    }

    private <T> ActionTicket<T> withNewCompletionStage(@NonNull CompletionStage<T> completionStage) {
        return new FPCActionTicket<>(actionExecutor, completionStage);
    }

    @Override
    public @NonNull <S> ActionTicket<S> thenApply(@NonNull Function1<? super R, ? extends S> after) {
        return withNewCompletionStage(completionStage.thenApply(after));
    }

    @Override
    public @NonNull <S> ActionTicket<S> thenExecute(@NonNull Class<? extends Action<R, S>> actionType) {
        return withNewCompletionStage(completionStage.thenCompose(r -> actionExecutor.pushAction(actionType,r)));
    }

    @Override
    public @NonNull <P, S> ActionTicket<S> thenExecute(@NonNull Class<? extends Action<P, S>> actionType, @NonNull P parameter) {
        return withNewCompletionStage(completionStage.thenCompose(r -> actionExecutor.pushAction(actionType,parameter)));
    }

    @Override
    public @NonNull <S> ActionTicket<S> thenExecuteAsync(@NonNull Class<? extends AsyncAction<R, S>> actionType) {
        return withNewCompletionStage(completionStage.thenCompose(r -> actionExecutor.pushAction(actionType, r).thenCompose(c -> c)));
    }

    @Override
    public @NonNull ActionTicket<R> onFailureReturn(@NonNull Function1<Throwable, ? extends R> action) {
        return withNewCompletionStage(completionStage.exceptionally(action));
    }

    @Override
    public @NonNull CompletionStage<R> asCompletionStage() {
        return completionStage;
    }
}
