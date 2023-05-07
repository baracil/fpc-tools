package fpc.tools.fx;

import fpc.tools.fp.Nil;
import fpc.tools.fp.Try0;
import fpc.tools.lang.Todo;

public class FXDebug {

    private static final Object REF = new Object();

    private static int depth = 0;

    public static void run(Runnable runnable, String message) {
        call(() -> {runnable.run();return Nil.NULL;},message);
    }

    public static <T, E extends Throwable> T call(Try0<T,E> call, String message) throws E {
        FXTools.checkIsFXThread();
        depth++;
        try {
            return Todo.TRACE_AROUND(REF, call, "  ".repeat(depth));
        } finally {
            depth--;
        }
    }

}
