package fpc.tools.action;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Function1;
import fpc.tools.fp.Nil;
import fpc.tools.fp.TryResult;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface ActionTicket<R> {

    ActionTicket<R> whenComplete(Consumer1<? super TryResult<? super Throwable, ? super R>> action);

    default ActionTicket<R> onFailureDo(Consumer1<? super Throwable> action) {
        return whenComplete(t -> t.ifFailedAccept(action));
    }


    ActionTicket<Nil> thenAccept(Consumer1<? super R> action);

    <S> ActionTicket<S> thenApply(Function1<? super R, ? extends S> after);

    <S> ActionTicket<S> thenExecute(Class<? extends Action<R, S>> actionType);

    <P,S> ActionTicket<S> thenExecute(Class<? extends Action<P, S>> actionType, P parameter);

    <S> ActionTicket<S> thenExecuteAsync(Class<? extends AsyncAction<R, S>> actionType);

    ActionTicket<R> onFailureReturn(Function1<Throwable, ? extends R> action);


    CompletionStage<R> asCompletionStage();
}
