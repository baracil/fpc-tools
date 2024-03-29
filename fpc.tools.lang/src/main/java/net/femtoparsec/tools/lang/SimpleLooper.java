package net.femtoparsec.tools.lang;

import fpc.tools.lang.LoopAction;
import fpc.tools.lang.Looper;
import fpc.tools.lang.ThrowableTool;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class SimpleLooper implements Looper {

    private final LoopAction loopAction;
    private final ExecutorService executorService;

    private @Nullable Runner runner = null;
    private @Nullable Future<?> future = null;

    public SimpleLooper(LoopAction loopAction, ExecutorService executorService) {
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
    public void startAndWaitForStart() {
        this.requestStop();
        runner = new Runner();
        future = executorService.submit(runner);
        try {
            runner.waitStartOfLoop();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
        private final Condition loopStarted = lock.newCondition();
        private volatile boolean started = false;

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


        public void waitStartOfLoop() throws InterruptedException {
            lock.lock();
            try {
                while (!started) {
                    loopStarted.await();
                }
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void run() {
            setStarted();
            final var next = loopAction.beforeLooping();
            if (next == LoopAction.NextState.STOP) {
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
                    LOG.warn("Ignored error : {}", e.getMessage(), e);
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

        private void setStarted() {
            lock.lock();
            try {
                started = true;
                loopStarted.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

}
