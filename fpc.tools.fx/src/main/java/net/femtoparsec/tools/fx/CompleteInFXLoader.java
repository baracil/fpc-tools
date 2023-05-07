package net.femtoparsec.tools.fx;

import fpc.tools.lang.Loader;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class CompleteInFXLoader<P,R> implements Loader<P,R> {

    private final Loader<P,R> original;

    @Override
    public CompletionStage<R> load(P parameter) {
        final CompletableFuture<R> inFx = new CompletableFuture<>();
        original.load(parameter)
                .whenComplete((r,t) -> {
                    if (t != null) {
                        inFx.completeExceptionally(t);
                    } else {
                        inFx.complete(r);
                    }
                });
        return inFx;
    }

    @Override
    public void cancelLoading() {
        original.cancelLoading();
    }

    @Override
    public Loader<P, R> duplicate() {
        return new CompleteInFXLoader<>(original.duplicate());
    }
}
