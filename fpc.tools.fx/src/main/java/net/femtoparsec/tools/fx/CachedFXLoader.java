package net.femtoparsec.tools.fx;

import fpc.tools.fx.FXLoader;
import fpc.tools.fx.FXLoadingResult;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Locale;

@RequiredArgsConstructor
public class CachedFXLoader implements FXLoader {

    @NonNull
    private final FXLoader delegate;

    private Reference<FXLoadingResult> reference = null;

    @Override
    public @NonNull FXLoadingResult load(@NonNull Locale locale) {
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
    public @NonNull FXLoader cached() {
        return this;
    }
}
