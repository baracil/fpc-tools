package fpc.tools.lang;

import fpc.tools.fp.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SmartLock implements Lock {

    public static SmartLock reentrant() {
        return new SmartLock(new ReentrantLock());
    }

    @Delegate
    private final Lock delegate;

    public <T,E extends Throwable> T getLocked(Try0<T,E> getter) throws E {
        lock();
        try {
            return getter.apply();
        } finally {
            unlock();
        }
    }

    public <A,T,E extends Throwable> T getLocked(Try1<? super A, ? extends T,E> getter, A a) throws E {
        return getLocked(() -> getter.apply(a));
    }

    public <A,B,T,E extends Throwable> T getLocked(Try2<? super A, ? super B, ? extends T,E> getter, A a, B b) throws E {
        return getLocked(() -> getter.apply(a,b));
    }

    public <E extends Throwable> void runLocked(TryConsumer0<E> action) throws E {
        getLocked(action.toTry());
    }

    public <A,E extends Throwable> void runLocked(TryConsumer1<? super A,E> action, A a) throws E {
        getLocked(() -> {action.accept(a);return Nil.NULL;});
    }

    public <A,B,E extends Throwable> void runLocked(TryConsumer2<? super A, ? super B,E> action, A a, B b) throws E {
        getLocked(() -> {action.accept(a,b);return Nil.NULL;});
    }


    public void await(Condition condition) throws InterruptedException {
        runLocked(condition::await);
    }
}
