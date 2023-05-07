package fpc.tools.fxmodel;

import fpc.tools.fx.FXLoader;
import fpc.tools.fx.FXLoaderFactory;

public class FXViewWithController implements FXView {

    private final FXLoader fxLoader;

    public FXViewWithController(FXLoaderFactory fxLoaderFactory, Class<?> controllerClass) {
        this.fxLoader = fxLoaderFactory.create(controllerClass);
    }

    @Override
    public FXViewInstance getViewInstance() {
        return FXViewInstance.with(fxLoader.load());
    }
}
