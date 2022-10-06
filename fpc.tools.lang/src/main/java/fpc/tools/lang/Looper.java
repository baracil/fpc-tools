package fpc.tools.lang;

import lombok.NonNull;
import net.femtoparsec.tools.lang.Loopers;
import net.femtoparsec.tools.lang.ScheduledLooper;
import net.femtoparsec.tools.lang.SimpleLooper;

import java.time.Duration;

public interface Looper {

    /**
     * Launch the looper
     */
    void start();

    void startAndWaitForStart();

    /**
     * Request the looper to stop and wait for the end of the loop
     *
     * @throws InterruptedException if the wait has been interrupted
     */
    void stop() throws InterruptedException;

    /**
     * request the loop to stop and return immediately
     */
    void requestStop();

    boolean isRunning();

    /**
     * Create a simple loop using a single thread executor
     *
     * @param action the action to loop
     * @return the newly created looper
     */
    static @NonNull Looper simple(@NonNull LoopAction action) {
        return new SimpleLooper(action, Loopers.EXECUTOR_SERVICE);
    }

    static @NonNull Looper scheduled(@NonNull LoopAction action, @NonNull Duration period) {
        return new ScheduledLooper(action, period, period);
    }

    static @NonNull Looper scheduled(@NonNull LoopAction action, @NonNull Duration delay, @NonNull Duration period) {
        return new ScheduledLooper(action, delay, period);
    }
}
