package fpc.tools.fxmodel;

import net.femtoparsec.tools.fxmodel.FPCCachedFXView;

public interface CachedFXView extends FXView {

    static CachedFXView cached(FXView fxView) {
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
