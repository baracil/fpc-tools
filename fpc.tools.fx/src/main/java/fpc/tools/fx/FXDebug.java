package fpc.tools.fx;

import fpc.tools.fp.Function0;
import fpc.tools.fp.Nil;
import fpc.tools.lang.Tools;
import lombok.NonNull;

public class FXDebug {

    private static final Object REF = new Object();

    private static int depth = 0;

    public static void run(@NonNull Runnable runnable, @NonNull String message) {
        call(() -> {runnable.run();return Nil.NULL;},message);
    }

    public static <T> @NonNull T call(@NonNull Function0<T> call, @NonNull String message) {
        FXTools.checkIsFXThread();
        depth++;
        try {
            return Tools.TRACE_AROUND(REF, call, "  ".repeat(depth));
        } finally {
            depth--;
        }
    }

}
