package fpc.tools.fxmodel;

import fpc.tools.fx.FXLoader;
import fpc.tools.fx.FXLoaderFactory;
import lombok.NonNull;

public class FXViewWithController implements FXView {

    @NonNull
    private final FXLoader fxLoader;

    public FXViewWithController(@NonNull FXLoaderFactory fxLoaderFactory, @NonNull Class<?> controllerClass) {
        this.fxLoader = fxLoaderFactory.create(controllerClass);
    }

    @Override
    public @NonNull FXViewInstance getViewInstance() {
        return FXViewInstance.with(fxLoader.load());
    }
}
