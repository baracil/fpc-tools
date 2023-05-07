package net.femtoparsec.tools.fxmodel;

import fpc.tools.fx.FXLoader;
import fpc.tools.fxmodel.FXView;
import fpc.tools.fxmodel.FXViewInstance;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FXViewWithLoader implements FXView {

    private final FXLoader fxLoader;

    @Override
    public FXViewInstance getViewInstance() {
        return new FPCFXViewInstance(fxLoader.load());
    }

}
