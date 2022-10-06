package fpc.tools.action;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Function1;
import fpc.tools.fp.Nil;
import fpc.tools.fp.TryResult;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

public interface ActionTicket<R> {

    @NonNull
    ActionTicket<R> whenComplete(@NonNull Consumer1<? super TryResult<? super Throwable, ? super R>> action);

    default ActionTicket<R> onFailureDo(@NonNull Consumer1<? super Throwable> action) {
        return whenComplete(t -> t.ifFailedAccept(action));
    }


    @NonNull
    ActionTicket<Nil> thenAccept(@NonNull Consumer1<? super R> action);

    @NonNull <S> ActionTicket<S> thenApply(@NonNull Function1<? super R, ? extends S> after);

    @NonNull <S> ActionTicket<S> thenExecute(@NonNull Class<? extends Action<R, S>> actionType);

    @NonNull <P,S> ActionTicket<S> thenExecute(@NonNull Class<? extends Action<P, S>> actionType, @NonNull P parameter);

    @NonNull <S> ActionTicket<S> thenExecuteAsync(@NonNull Class<? extends AsyncAction<R, S>> actionType);

    @NonNull ActionTicket<R> onFailureReturn(@NonNull Function1<Throwable, ? extends R> action);


    @NonNull CompletionStage<R> asCompletionStage();
}
