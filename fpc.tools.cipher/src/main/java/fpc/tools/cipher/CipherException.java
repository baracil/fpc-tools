package fpc.tools.cipher;

import fpc.tools.fp.Try0;
import fpc.tools.fp.Try1;
import fpc.tools.fp.TryConsumer0;
import fpc.tools.fp.TryConsumer1;

public class CipherException extends RuntimeException {

    public CipherException(Throwable cause) {
        super(cause);
    }

    public CipherException(String message) {
        super(message);
    }

    public static void wrapRun(TryConsumer0<? extends Throwable> action) {
        try {
            action.accept();
        } catch (Throwable t) {
            throw new CipherException(t);
        }
    }

    public static <A> void wrapRun(TryConsumer1<? super A,? extends Throwable> action, A a) {
        try {
            action.accept(a);
        } catch (Throwable t) {
            throw new CipherException(t);
        }
    }

    public static <Z> Z wrapCall(Try0<Z,? extends Throwable> action) {
        try {
            return action.apply();
        } catch (Throwable t) {
            throw new CipherException(t);
        }
    }

    public static <A,Z> Z wrapCall(Try1<A,Z,? extends Throwable> action, A a) {
        try {
            return action.apply(a);
        } catch (Throwable t) {
            throw new CipherException(t);
        }
    }
}
