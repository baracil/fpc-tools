package net.femtoparsec.tools.action;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import fpc.tools.action.Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FPCExecutor implements Executor {

    private final ExecutorService SINGLE_THREAD_EXECUTOR_SERVICE = Executors.newSingleThreadExecutor(
            new ThreadFactoryBuilder().setNameFormat("Sync Action Executor %d").setDaemon(false).build()
    );

    private final ExecutorService CACHED_THREAD_EXECUTOR_SERVICE = Executors.newCachedThreadPool(
            new ThreadFactoryBuilder().setNameFormat("Async Action Executor %d").setDaemon(false).build()
    );


    @Override
    public void executeAsync(Runnable runnable) {
        CACHED_THREAD_EXECUTOR_SERVICE.submit(runnable);
    }

    @Override
    public void executeSync(Runnable runnable) {
        SINGLE_THREAD_EXECUTOR_SERVICE.submit(runnable);
    }

    @Override
    public void shutdown() {
        SINGLE_THREAD_EXECUTOR_SERVICE.shutdown();
        CACHED_THREAD_EXECUTOR_SERVICE.shutdown();
    }
}
