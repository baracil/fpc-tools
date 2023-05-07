package fpc.tools.fx;

import fpc.tools.lang.ServiceLoaderHelper;
import lombok.NonNull;

import java.util.ServiceLoader;

public interface KeyTrackerFactory {

    KeyTracker create();

    static KeyTrackerFactory getInstance() {
        return Holder.INSTANCE;
    }

    class Holder {
        private static final KeyTrackerFactory INSTANCE = ServiceLoaderHelper.load(ServiceLoader.load(KeyTrackerFactory.class));
    }
}
