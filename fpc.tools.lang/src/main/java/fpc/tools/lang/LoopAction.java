package fpc.tools.lang;

import jakarta.annotation.Nullable;

public interface LoopAction {

    /**
     * Called before looping
     * @return the next state (should continue or stop)
     */
    default NextState beforeLooping() {
        return NextState.CONTINUE;
    }

    /**
     * Perform one iteration
     * @return the next state (should continue or stop)
     * @throws Throwable if an error occurs
     */
    NextState performOneIteration() throws Throwable;

    /**
     * @param error the error that occurred during the last iteration
     * @return true if the loop should be stopped
     */
    default boolean shouldStopOnError(Throwable error) {
        return true;
    }

    /**
     * Call when the loop is done
     * @param error the error that stop the iteration (can be null if the loop has been stop with {@link NextState#STOP}
     */
    default void onDone(@Nullable Throwable error) {}

    enum NextState {
        CONTINUE,
        STOP
    }
}
