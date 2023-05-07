package net.femtoparsec.tools.lang;

import fpc.tools.lang.WaitStrategy;
import lombok.NonNull;

import java.time.Duration;

public class ThreadSleepWaitStrategy implements WaitStrategy {

    public static ThreadSleepWaitStrategy create() {
        return Holder.INSTANCE;
    }


    @Override
    public void waitFor(Duration duration) throws InterruptedException {
        if (!duration.isNegative() && !duration.isZero()) {
            Thread.sleep(duration.toMillis(), duration.toNanosPart());
        }
    }

    private static class Holder {
        private static final ThreadSleepWaitStrategy INSTANCE = new ThreadSleepWaitStrategy();
    }

}
