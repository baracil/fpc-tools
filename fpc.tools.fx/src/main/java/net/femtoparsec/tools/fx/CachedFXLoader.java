package net.femtoparsec.tools.fx;

import fpc.tools.fx.FXLoader;
import fpc.tools.fx.FXLoadingResult;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Locale;

@RequiredArgsConstructor
public class CachedFXLoader implements FXLoader {

    private final FXLoader delegate;

    private @Nullable Reference<FXLoadingResult> reference = null;

    @Override
    public FXLoadingResult load(Locale locale) {
        final FXLoadingResult cached = reference != null ? reference.get():null;
        final FXLoadingResult result;
        if (cached != null) {
            result = cached;
        } else {
            result = delegate.load();
            reference = new SoftReference<>(result);
        }
        return result;
    }

    @Override
    public FXLoader cached() {
        return this;
    }
}
