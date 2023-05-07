package net.femtoparsec.tools.advanced.chat;

import fpc.tools.advanced.chat.Message;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author Bastien Aracil
 **/
@RequiredArgsConstructor
public abstract class AbstractPostData<A,S extends Message, M> implements PostData<A,M> {

    private final S message;

    private final CompletableFuture<A> completableFuture = new CompletableFuture<>();

    @Override
    public S message() {
        return message;
    }

    @Override
    public CompletionStage<A> completionStage() {
        return completableFuture;
    }

    protected void completeWith(A value) {
        completableFuture.complete(value);
    }

    protected void completeExceptionallyWith(Throwable error) {
        completableFuture.completeExceptionally(error);
    }

    @Override
    public void onMessagePostFailure(Throwable t) {
        completableFuture.completeExceptionally(t);
    }

    public boolean isCompleted() {
        return completableFuture.isDone();
    }

}
