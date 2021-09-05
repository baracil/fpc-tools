package fpc.tools.fxmodel;

import fpc.tools.fx.FXLoader;
import lombok.NonNull;
import net.femtoparsec.tools.fxmodel.FXViewWithLoader;

public interface FXView {

    @NonNull
    static FXView create(@NonNull FXLoader fxLoader) {
        return new FXViewWithLoader(fxLoader);
    }

    @NonNull
    FXViewInstance getViewInstance();

    default CachedFXView cached() {
        return CachedFXView.cached(this);
    }
}
