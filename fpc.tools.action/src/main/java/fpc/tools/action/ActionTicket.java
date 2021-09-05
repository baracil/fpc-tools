package fpc.tools.action;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Nil;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ActionTicket<R> {

    @NonNull
    ActionTicket<R> whenComplete(@NonNull BiConsumer<? super R, ? super Throwable> action);

    @NonNull
    ActionTicket<Nil> thenAccept(@NonNull Consumer<? super R> action);

    @NonNull
    <S> ActionTicket<S> thenApply(@NonNull Function1<? super R, ? extends S> after);

    @NonNull
    <S> ActionTicket<S> thenExecute(@NonNull Class<? extends Action<R,S>> actionType);

    @NonNull
    CompletionStage<R> asCompletionStage();
}
