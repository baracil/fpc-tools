package net.femtoparsec.tools.lang;

import fpc.tools.lang.LoopAction;
import fpc.tools.lang.Looper;
import fpc.tools.lang.ThrowableTool;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class SimpleLooper implements Looper {

    private final @NonNull LoopAction loopAction;
    private final @NonNull ExecutorService executorService;

    private Runner runner = null;
    private Future<?> future = null;

    public SimpleLooper(@NonNull LoopAction loopAction, @NonNull ExecutorService executorService) {
        this.loopAction = loopAction;
        this.executorService = executorService;
    }

    @Override
    @Synchronized
    public void start() {
        this.requestStop();
        runner = new Runner();
        future = executorService.submit(runner);
    }

    @Override
    @Synchronized
    public void stop() throws InterruptedException {
        this.requestStop();
        final var r = runner;
        if (r != null) {
            r.waitEndOfLoop();
        }
    }

    @Override
    @Synchronized
    public void requestStop() {
        if (future != null) {
            future.cancel(true);
        }
    }

    @Override
    public boolean isRunning() {
        final var future = this.future;
        return future != null && !future.isCancelled();
    }

    private class Runner implements Runnable {

        private final Lock lock = new ReentrantLock();
        private final Condition loopDone = lock.newCondition();

        public void waitEndOfLoop() throws InterruptedException {
            lock.lock();
            try {
                while (SimpleLooper.this.runner != null) {
                    loopDone.await();
                }
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void run() {

            if (loopAction.beforeLooping() == LoopAction.NextState.STOP) {
                return;
            }
            Throwable error = null;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    final var state = loopAction.performOneIteration();
                    if (state == LoopAction.NextState.STOP) {
                        break;
                    }
                } catch (Throwable e) {
                    if (ThrowableTool.isCausedByInterruption(e) || loopAction.shouldStopOnError(e)) {
                        error = e;
                        break;
                    }
                    LOG.warn("Ignored error : {}",e.getMessage(),e);
                }
            }
            loopAction.onDone(error);
            lock.lock();
            try {
                SimpleLooper.this.runner = null;
                loopDone.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

}
