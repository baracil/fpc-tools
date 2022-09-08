package fpc.tools.cipher;

import fpc.tools.fp.Try0;
import fpc.tools.fp.Try1;
import fpc.tools.fp.TryConsumer0;
import fpc.tools.fp.TryConsumer1;
import lombok.NonNull;

public class CipherException extends RuntimeException {

    public CipherException(Throwable cause) {
        super(cause);
    }

    public CipherException(String message) {
        super(message);
    }

    public static void wrapRun(@NonNull TryConsumer0<? extends Throwable> action) {
        try {
            action.accept();
        } catch (Throwable t) {
            throw new CipherException(t);
        }
    }

    public static <A> void wrapRun(@NonNull TryConsumer1<? super A,? extends Throwable> action, @NonNull A a) {
        try {
            action.accept(a);
        } catch (Throwable t) {
            throw new CipherException(t);
        }
    }

    public static <Z> @NonNull Z wrapCall(@NonNull Try0<Z,? extends Throwable> action) {
        try {
            return action.apply();
        } catch (Throwable t) {
            throw new CipherException(t);
        }
    }

    public static <A,Z> @NonNull Z wrapCall(@NonNull Try1<A,Z,? extends Throwable> action, @NonNull A a) {
        try {
            return action.apply(a);
        } catch (Throwable t) {
            throw new CipherException(t);
        }
    }
}
