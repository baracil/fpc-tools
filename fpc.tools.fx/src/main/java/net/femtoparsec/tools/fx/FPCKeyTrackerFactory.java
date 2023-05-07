package net.femtoparsec.tools.fx;

import fpc.tools.fx.KeyTracker;
import fpc.tools.fx.KeyTrackerFactory;
import fpc.tools.lang.Priority;

@Priority(Integer.MIN_VALUE)
public class FPCKeyTrackerFactory implements KeyTrackerFactory {

    @Override
    public KeyTracker create() {
        return new FPCKeyTracker();
    }

}
