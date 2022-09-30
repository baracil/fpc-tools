package fpc.tools.lang;

import lombok.NonNull;
import net.femtoparsec.tools.lang.SpinWaitStrategy;
import net.femtoparsec.tools.lang.ThreadSleepWaitStrategy;

import java.time.Duration;

public interface WaitStrategy {

    void waitFor(@NonNull Duration duration) throws InterruptedException;


    static WaitStrategy create() {
        return threadSleep();
    }

    @NonNull
    static WaitStrategy spinWait() {
        return SpinWaitStrategy.create();
    }

    @NonNull
    static WaitStrategy threadSleep() {
        return ThreadSleepWaitStrategy.create();
    }
}
