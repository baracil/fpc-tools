package net.femtoparsec.tools.state;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Function0;
import fpc.tools.fp.Function1;
import fpc.tools.lang.ThreadBuilder;
import fpc.tools.lang.ThrowableTool;
import fpc.tools.state.Mutation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author Bastien Aracil
 */
@RequiredArgsConstructor
public class DefaultUpdater<R> implements Updater<R> {

    @NonNull
    private final BlockingDeque<UpdateInformation<R,?>> updateInformationQueue = new LinkedBlockingDeque<>();

    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();

    private Thread runningThread = null;


    @Override
    public void start() {
        runLocked(() -> {
            this.stop();
            runningThread = ThreadBuilder.builder(new Runner()).daemon(true).name("Updater Thread").build();
            runningThread.start();
        });
    }

    @Override
    public void stop() {
        runLocked(() -> {
            if (runningThread != null) {
                runningThread.interrupt();
            }
            boolean interrupted = false;
            while (runningThread != null) {
                try {
                    done.await();
                } catch (InterruptedException e) {
                    //Interruption request has been received. We must wait for
                    //the underlying thread to finish.
                    interrupted = true;
                }
            }
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        });
    }

    private void runLocked(@NonNull Runnable runnable) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public @NonNull <S> CompletionStage<UpdateResult<R, S>> offerUpdatingOperation(
            @NonNull Mutation<R> mutation,
            @NonNull Function0<? extends R> rootStateGetter,
            @NonNull Consumer1<? super R> newRootStateConsumer,
            @NonNull Function1<? super R, ? extends S> subStateGetter) {
        final Update<R, S> update = new Update<>(rootStateGetter, newRootStateConsumer,mutation, subStateGetter);
        final UpdateInformation<R,S> updateInformation = new UpdateInformation<>(update, new CompletableFuture<>());
        runLocked(() -> {
            if (isRunning()) {
                try {
                    this.updateInformationQueue.addLast(updateInformation);
                } catch (Throwable t) {
                    ThrowableTool.interruptIfCausedByInterruption(t);
                    updateInformation.getCompletableFuture().completeExceptionally(new InterruptedException());
                }
            } else {
                updateInformation.getCompletableFuture().completeExceptionally(new InterruptedException());
            }
        });
        return updateInformation.completableFuture;
    }

    private boolean isRunning() {
        return runningThread != null;
    }

    private class Runner implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    final UpdateInformation<R,?> updateInformation = updateInformationQueue.takeFirst();
                    updateInformation.performUpdate();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            lock.lock();
            try {
                runningThread = null;
                final Throwable error = new InterruptedException();
                updateInformationQueue.forEach(i -> i.completableFuture.completeExceptionally(error));
                updateInformationQueue.clear();
                done.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }

    @Value
    private static class UpdateInformation<R,S> {

        @NonNull Update<R,S> updater;

        @NonNull CompletableFuture<UpdateResult<R,S>> completableFuture;

        public void completeExceptionally(@NonNull Throwable error) {
            completableFuture.completeExceptionally(error);
        }

        public void performUpdate() {
            try {
                final UpdateResult<R, S> result = updater.performMutation();
                completableFuture.complete(result);
            } catch (Throwable t) {
                ThrowableTool.interruptIfCausedByInterruption(t);
                completeExceptionally(t);
            }
        }
    }

}
