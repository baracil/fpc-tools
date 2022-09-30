package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.Message;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author Bastien Aracil
 **/
@RequiredArgsConstructor
public abstract class AbstractPostData<A,S extends Message, M> implements PostData<A,M> {

    @NonNull
    private final S message;

    @NonNull
    private final CompletableFuture<A> completableFuture = new CompletableFuture<>();

    @Override
    public @NonNull S message() {
        return message;
    }

    @Override
    public @NonNull CompletionStage<A> completionStage() {
        return completableFuture;
    }

    protected void completeWith(@NonNull A value) {
        completableFuture.complete(value);
    }

    protected void completeExceptionallyWith(@NonNull Throwable error) {
        completableFuture.completeExceptionally(error);
    }

    @Override
    public void onMessagePostFailure(@NonNull Throwable t) {
        completableFuture.completeExceptionally(t);
    }

    public boolean isCompleted() {
        return completableFuture.isDone();
    }

}
