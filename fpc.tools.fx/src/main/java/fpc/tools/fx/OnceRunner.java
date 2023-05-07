package fpc.tools.fx;

import javafx.application.Platform;
import lombok.NonNull;

public class OnceRunner {

    private boolean updating = false;

    public void run(Runnable runnable) {
        assert Platform.isFxApplicationThread();
        if (updating) {
            return;
        }
        updating = true;
        try {
            runnable.run();
        } finally {
            updating = false;
        }
    }



}
