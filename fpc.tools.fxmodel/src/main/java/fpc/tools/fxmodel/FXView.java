package fpc.tools.fxmodel;

import fpc.tools.fx.FXLoader;
import net.femtoparsec.tools.fxmodel.FXViewWithLoader;

public interface FXView {

    static FXView create(FXLoader fxLoader) {
        return new FXViewWithLoader(fxLoader);
    }

    FXViewInstance getViewInstance();

    default CachedFXView cached() {
        return CachedFXView.cached(this);
    }
}
