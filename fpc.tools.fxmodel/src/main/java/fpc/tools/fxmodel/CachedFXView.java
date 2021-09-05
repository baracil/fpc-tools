package fpc.tools.fxmodel;

import lombok.NonNull;
import net.femtoparsec.tools.fxmodel.FPCCachedFXView;

public interface CachedFXView extends FXView {

    static CachedFXView cached(@NonNull FXView fxView) {
        if (fxView instanceof CachedFXView) {
            return (CachedFXView) fxView;
        }
        return new FPCCachedFXView(fxView);
    }

    /**
     * Invalidate the cache
     */
    void invalidate();

}
