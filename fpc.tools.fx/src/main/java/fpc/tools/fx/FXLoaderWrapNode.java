package fpc.tools.fx;

import fpc.tools.fp.Function2;
import javafx.scene.Node;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.femtoparsec.tools.fx.CachedFXLoader;

import java.util.Locale;

@RequiredArgsConstructor
public class FXLoaderWrapNode implements FXLoader {

    @NonNull
    private final FXLoader fxLoader;

    private final Function2<? super String, ? super Node, ? extends Node> wrapper;

    @Override
    public @NonNull FXLoadingResult load(@NonNull Locale locale) {
        final var result = fxLoader.load();
        final Object controller = result.getController();
        final Object root = result.getRoot();
        if (root instanceof Node) {
            final Node wrapped = wrapper.apply(controller.getClass().getSimpleName(),(Node)root);
            return new FXLoadingResult(controller,wrapped);
        }
        return result;
    }

    @Override
    public @NonNull FXLoader cached() {
        return new CachedFXLoader(this);
    }
}
