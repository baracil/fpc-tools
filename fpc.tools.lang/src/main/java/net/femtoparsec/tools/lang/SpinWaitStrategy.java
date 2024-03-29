package net.femtoparsec.tools.lang;

import fpc.tools.lang.WaitStrategy;

import java.time.Duration;

public class SpinWaitStrategy implements WaitStrategy {

    public static SpinWaitStrategy create() {
        return Holder.INSTANCE;
    }

    @Override
    public void waitFor(Duration duration) throws InterruptedException {
        final long target = System.nanoTime()+duration.toNanos();
        while ((target-System.nanoTime())>0) {
            checkInterruption();
            Thread.onSpinWait();
        }
    }

    private void checkInterruption() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
    }

    private static class Holder {
        private static final SpinWaitStrategy INSTANCE = new SpinWaitStrategy();
    }
}
