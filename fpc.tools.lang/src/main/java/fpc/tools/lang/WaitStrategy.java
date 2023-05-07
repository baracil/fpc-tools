package fpc.tools.lang;

import net.femtoparsec.tools.lang.SpinWaitStrategy;
import net.femtoparsec.tools.lang.ThreadSleepWaitStrategy;

import java.time.Duration;

public interface WaitStrategy {

    void waitFor(Duration duration) throws InterruptedException;


    static WaitStrategy create() {
        return threadSleep();
    }

    static WaitStrategy spinWait() {
        return SpinWaitStrategy.create();
    }

    static WaitStrategy threadSleep() {
        return ThreadSleepWaitStrategy.create();
    }
}
