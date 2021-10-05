package fpc.tools.action;

import lombok.NonNull;
import net.femtoparsec.tools.action.FPCExecutor;

public interface Executor {

    static @NonNull Executor create() {
        return new FPCExecutor();
    }

    void executeAsync(Runnable runnable);

    void executeSync(Runnable runnable);

    void shutdown();
}
