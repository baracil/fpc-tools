package fpc.tools.lang;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;
import net.femtoparsec.tools.lang.SimpleLooper;

import java.util.concurrent.Executors;

public interface Looper {

    /**
     * Launch the looper
     */
    void start();

    /**
     * Request the looper to stop and wait for the end of the loop
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
     * @param action the action to loop
     * @param daemon define if the thread used by the loop should be a daemon thread or not
     * @return the newly created looper
     */
    static @NonNull Looper simple(@NonNull LoopAction action, boolean daemon) {
        final var threadFactory = new ThreadFactoryBuilder().setDaemon(daemon).build();
        final var executorService = Executors.newSingleThreadExecutor(threadFactory);
        return new SimpleLooper(action, executorService);
    }
}
