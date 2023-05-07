package fpc.tools.fxmodel;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmptyFXView implements FXView {

    public static EmptyFXView create() {
        return INSTANCE;
    }

    private static final EmptyFXView INSTANCE = new EmptyFXView();

    @Override
    public FXViewInstance getViewInstance() {
        return FXViewInstance.EMPTY;
    }

}
