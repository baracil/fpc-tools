package net.femtoparsec.tools.fxmodel;

import fpc.tools.fxmodel.CachedFXView;
import fpc.tools.fxmodel.FXView;
import fpc.tools.fxmodel.FXViewInstance;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Optional;

@RequiredArgsConstructor
public class FPCCachedFXView implements CachedFXView {

    @NonNull
    private final FXView reference;

    private Reference<FXViewInstance> cache = null;

    @Override
    public void invalidate() {
        cache = null;
    }

    @Override
    public @NonNull FXViewInstance getViewInstance() {
        return Optional.ofNullable(cache)
                       .map(Reference::get)
                       .orElseGet(this::getNewInstance);
    }

    public FXViewInstance getNewInstance() {
        final FXViewInstance newValue = reference.getViewInstance();
        this.cache = new SoftReference<>(newValue);
        return newValue;
    }
}
