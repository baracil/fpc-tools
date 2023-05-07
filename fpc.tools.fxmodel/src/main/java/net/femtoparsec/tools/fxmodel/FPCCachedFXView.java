package net.femtoparsec.tools.fxmodel;

import fpc.tools.fxmodel.CachedFXView;
import fpc.tools.fxmodel.FXView;
import fpc.tools.fxmodel.FXViewInstance;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Optional;

@RequiredArgsConstructor
public class FPCCachedFXView implements CachedFXView {

    private final FXView reference;

    private @Nullable Reference<FXViewInstance> cache = null;

    @Override
    public void invalidate() {
        cache = null;
    }

    @Override
    public FXViewInstance getViewInstance() {
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
