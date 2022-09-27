package fpc.tools.lang;

import fpc.tools.fp.Predicate1;
import fpc.tools.fp.Try0;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;


@Slf4j
public class Todo {

    public static <T> T TODO() {
        throw new NotImplemented();
    }

    public static <T> T TODO(@NonNull String message) {
        throw new NotImplemented(message);
    }

    public static <T> @NonNull T WARN(@NonNull Object callerObject, @NonNull T value, @NonNull String message) {
        display(callerObject,message,false);
        return value;
    }

    public static <T> @NonNull T TRACE(@NonNull Object callerObject, @NonNull T value, @NonNull String message) {
        display(callerObject,message,false);
        return value;
    }

    public static void TRACE(@NonNull Object callerObject, @NonNull String message) {
        display(callerObject,message,false);
    }
    public static void TRACE_ERR(@NonNull Object callerObject, @NonNull String message) {
        display(callerObject,message,true);
    }

    public static void TRACE_AROUND(@NonNull Object callerObject, @NonNull Runnable action, @NonNull String message) {
        display(callerObject,message,false);
        display(callerObject,"   : Start",false);
        try {
            action.run();
            display(callerObject,"   : Done -> OK",false);
        } catch (Throwable t) {
            display(callerObject,"   : Done -> OK"+t.getMessage(),true);
            throw t;
        }
    }

    public static <T,E extends Throwable> T TRACE_AROUND(@NonNull Object callerObject, @NonNull Try0<T,E> action, @NonNull String message) throws E {
        display(callerObject,message+" : Start",false);
        try {
            var value = action.apply();
            display(callerObject,message+" : Done -> "+value,false);
            return value;
        } catch (Throwable t) {
            display(callerObject,message+" : Done -> "+t.getMessage(),true);
            throw t;
        }
    }

    public static <T> T TRACE_AROUND(@NonNull Object callerObject, @NonNull Supplier<T> action, @NonNull String message) {
        display(callerObject,message+" : Start",false);
        try {
            var value = action.get();
            display(callerObject,message+" : Done -> "+value,false);
            return value;
        } catch (Throwable t) {
            display(callerObject,message+" : Done -> "+t.getMessage(),true);
            throw t;
        }
    }

    private static void display(@NonNull Object caller, @NonNull String message, boolean error) {
        final String msg = "["+caller.getClass().getSimpleName()+" #"+Integer.toHexString(System.identityHashCode(caller))+"] "+message;
        ((error)?System.err:System.out).println(msg);
    }


    public static int clampInt(int value, int minValue, int maxValue) {
        if (value<minValue) {
            return minValue;
        }
        return Math.min(value, maxValue);
    }

    public static <A,T> boolean same(A a1, A a2, @NonNull Function<? super A, ? extends T> getter) {
        if (a1 == a2) {
            return true;
        }
        else if (a1 == null || a2 == null) {
            return false;
        }
        return Objects.equals(getter.apply(a1),getter.apply(a2));
    }

    public static <A,T> boolean notSame(A a1, A a2, @NonNull Function<? super A, ? extends T> getter) {
        return !same(a1,a2,getter);
    }

    public static <A> Predicate1<Function<? super A, ?>> tester(A a1, A a2) {
        if (a1 == a2) {
            return f -> true;
        }
        else if (a1 == null || a2 == null) {
            return f -> false;
        }
        return f -> Objects.equals(f.apply(a1),f.apply(a2));

    }

    public static void dumpStack() {
        Arrays.stream(Thread.currentThread().getStackTrace())
              .forEach(System.err::println);
    }
}
