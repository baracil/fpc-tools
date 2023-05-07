package net.femtoparsec.tools.lang;

import fpc.tools.lang.LoopAction;
import fpc.tools.lang.Looper;
import fpc.tools.lang.SmartLock;
import fpc.tools.lang.ThrowableTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

@RequiredArgsConstructor
@Slf4j
public class ScheduledLooper implements Looper, Runnable {

    private final SmartLock lock = SmartLock.reentrant();
    private final Condition loopStopped = lock.newCondition();
    private final Condition loopStarted = lock.newCondition();
    private volatile boolean started = false;


    private final LoopAction loopAction;
    private final Duration delay;
    private final Duration period;
    private State state = State.STOPPED;


    @Override
    public void start() {
        lock.runLocked(() -> {
                    if (state == State.STOPPED) {
                        state = State.STARTING;
                        firstSchedule();
                    }
                }
        );
    }

    @Override
    public void startAndWaitForStart() {
        try {
            lock.runLocked(() -> {
                if (state == State.STOPPED) {
                    started = false;
                    state = State.STARTING;
                    firstSchedule();
                    while (!started) {
                        loopStarted.await();
                    }
                }
            });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void stop() throws InterruptedException {
        requestStop();
        lock.lock();
        try {
            while (state != State.STOPPED) {
                loopStopped.await();
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void requestStop() {
        lock.runLocked(() -> {
            if (state == State.STOPPED) {
                return;
            }
            state = State.STOPPING;
        });
    }

    @Override
    public boolean isRunning() {
        return state != State.STOPPED && state != State.STOPPING;
    }

    @Override
    public void run() {
        lock.runLocked(this::doRun);
    }

    private void setState(State state) {
        lock.runLocked(() -> this.state = state);
    }

    public void doRun() {
        if (state == State.STARTING) {
            lock.runLocked(() -> {
                started = true;
                loopStarted.signalAll();
            });
            if (loopAction.beforeLooping() == LoopAction.NextState.STOP) {
                setState(State.STOPPING);
            } else {
                setState(State.STARTED);
            }
        }

        Throwable error = null;
        boolean done = false;
        if (state == State.STARTED) {
            try {
                done = loopAction.performOneIteration() == LoopAction.NextState.STOP;
            } catch (Throwable e) {
                final var interrupted = ThrowableTool.isCausedByInterruption(e);
                if (interrupted || loopAction.shouldStopOnError(e)) {
                    error = e;
                    done = true;
                } else {
                    LOG.warn("Ignored error : {}", e.getMessage(), e);
                }
            }
        }

        if (done) {
            setState(State.STOPPING);
            loopAction.onDone(error);
            setState(State.STOPPED);
        } else {
            schedule();
        }

    }

    private void schedule() {
        Loopers.SCHEDULED_EXECUTOR_SERVICE.schedule(this, period.toMillis(), TimeUnit.MILLISECONDS);
    }

    private void firstSchedule() {
        Loopers.SCHEDULED_EXECUTOR_SERVICE.schedule(this, delay.toMillis(), TimeUnit.MILLISECONDS);
    }

    private enum State {
        STARTED,
        STARTING,
        STOPPED,
        STOPPING
    }
}
