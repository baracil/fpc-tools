package net.femtoparsec.tools.lang;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fpc.tools.fp.Function1;
import fpc.tools.lang.Loader;
import fpc.tools.lang.ThrowableTool;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class BasicLoader<P, R> implements Loader<P,R> {

    @NonNull
    public static <P,R> BasicLoader<P,R> of(@NonNull Function1<? super P, ? extends R> loadingFunction) {
        return new BasicLoader<>(loadingFunction);
    }

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder()
                    .setNameFormat("Loader - %d")
                    .setDaemon(true)
                    .build()
    );

    @NonNull
    private final Function1<? super P, ? extends R> function;

    private Runner runner = null;

    @Synchronized
    public @NonNull CompletionStage<R> load(@NonNull P parameter) {
        this.cancelLoading();
        assert runner== null;
        final Runner runner = new Runner(parameter);
        EXECUTOR_SERVICE.submit(runner);
        return runner.getCompletableFuture();
    }

    @Synchronized
    public void cancelLoading() {
        if (runner != null) {
            runner.cancel();
        }
        runner = null;
    }

    @NonNull
    public BasicLoader<P,R> duplicate() {
        return new BasicLoader<>(function);
    }

    @RequiredArgsConstructor
    private class Runner implements Runnable {

        private Thread thread = null;

        private boolean cancelled = false;

        @NonNull
        private final P parameter;

        @NonNull
        @Getter
        private final CompletableFuture<R> completableFuture = new CompletableFuture<>();

        public void cancel() {
            this.cancelled = true;
            Optional.ofNullable(thread).ifPresent(Thread::interrupt);
        }

        @Override
        public void run() {
            this.thread = Thread.currentThread();
            if (cancelled || this.thread.isInterrupted()) {
                completableFuture.completeExceptionally(new InterruptedException());
            }
            try {
                completableFuture.complete(function.f(parameter));
            } catch (Throwable t) {
                ThrowableTool.interruptIfCausedByInterruption(t);
                completableFuture.completeExceptionally(t);
            }
         }
    }
}
