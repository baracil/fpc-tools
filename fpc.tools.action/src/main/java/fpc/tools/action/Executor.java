package fpc.tools.action;

import net.femtoparsec.tools.action.FPCExecutor;

public interface Executor {

    static Executor create() {
        return new FPCExecutor();
    }

    void executeAsync(Runnable runnable);

    void executeSync(Runnable runnable);

    void shutdown();
}
