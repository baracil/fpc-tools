package net.femtoparsec.tools.fx;

import fpc.tools.fx.FXLoader;
import fpc.tools.fx.FXLoadingResult;
import javafx.scene.Node;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Locale;

@RequiredArgsConstructor
public class FXLoaderSettingUserData implements FXLoader {

    private final @NonNull FXLoader delegate;

    private final @NonNull Object propertyKey;

    @Override
    public @NonNull FXLoadingResult load(@NonNull Locale locale) {

        final FXLoadingResult result = delegate.load(locale);
        final Reference<Object> reference = new WeakReference<>(result.getController());
        result.getRoot(Node.class)
              .map(n -> n.getProperties().put(propertyKey,reference));
        return result;
    }

    @Override
    public @NonNull FXLoader cached() {
        return new CachedFXLoader(this);
    }
}
