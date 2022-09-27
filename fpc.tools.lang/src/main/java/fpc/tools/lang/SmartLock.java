package fpc.tools.lang;

import fpc.tools.fp.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SmartLock implements Lock {

    public static @NonNull SmartLock reentrant() {
        return new SmartLock(new ReentrantLock());
    }

    @Delegate
    private final @NonNull Lock delegate;

    public <T> @NonNull T getLocked(@NonNull Function0<T> getter) {
        lock();
        try {
            return getter.get();
        } finally {
            unlock();
        }
    }

    public <A,T> @NonNull T getLocked(@NonNull Function1<? super A, ? extends T> getter, @NonNull A a) {
        return getLocked(() -> getter.apply(a));
    }

    public <A,B,T> @NonNull T getLocked(@NonNull Function2<? super A, ? super B, ? extends T> getter, @NonNull A a, @NonNull B b) {
        return getLocked(() -> getter.apply(a,b));
    }

    public void runLocked(@NonNull Consumer0 action) {
        getLocked(action.toFunction());
    }

    public <A> void runLocked(@NonNull Consumer1<? super A> action, @NonNull A a) {
        getLocked(() -> {action.accept(a);return Nil.NULL;});
    }

    public <A,B> void runLocked(@NonNull Consumer2<? super A, ? super B> action, @NonNull A a, @NonNull B b) {
        getLocked(() -> {action.accept(a,b);return Nil.NULL;});
    }


}
