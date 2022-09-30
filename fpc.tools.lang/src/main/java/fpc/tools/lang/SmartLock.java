package fpc.tools.lang;

import fpc.tools.fp.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SmartLock implements Lock {

    public static @NonNull SmartLock reentrant() {
        return new SmartLock(new ReentrantLock());
    }

    @Delegate
    private final @NonNull Lock delegate;

    public <T,E extends Throwable> @NonNull T getLocked(@NonNull Try0<T,E> getter) throws E {
        lock();
        try {
            return getter.apply();
        } finally {
            unlock();
        }
    }

    public <A,T,E extends Throwable> @NonNull T getLocked(@NonNull Try1<? super A, ? extends T,E> getter, @NonNull A a) throws E {
        return getLocked(() -> getter.apply(a));
    }

    public <A,B,T,E extends Throwable> @NonNull T getLocked(@NonNull Try2<? super A, ? super B, ? extends T,E> getter, @NonNull A a, @NonNull B b) throws E {
        return getLocked(() -> getter.apply(a,b));
    }

    public <E extends Throwable> void runLocked(@NonNull TryConsumer0<E> action) throws E {
        getLocked(action.toTry());
    }

    public <A,E extends Throwable> void runLocked(@NonNull TryConsumer1<? super A,E> action, @NonNull A a) throws E {
        getLocked(() -> {action.accept(a);return Nil.NULL;});
    }

    public <A,B,E extends Throwable> void runLocked(@NonNull TryConsumer2<? super A, ? super B,E> action, @NonNull A a, @NonNull B b) throws E {
        getLocked(() -> {action.accept(a,b);return Nil.NULL;});
    }


    public void await(Condition condition) throws InterruptedException {
        runLocked(condition::await);
    }
}
