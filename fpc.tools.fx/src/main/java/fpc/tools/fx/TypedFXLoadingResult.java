package fpc.tools.fx;

import fpc.tools.lang.CastTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TypedFXLoadingResult<C> {

    private final C controller;

    private final Object root;

    public <T> Optional<T> getRoot(Class<T> rootType) {
        return CastTool.as(rootType,root);
    }


    @SuppressWarnings("unchecked")
    public <T> T getRoot() {
        return (T)root;
    }

    @SuppressWarnings("unchecked")
    public C getController() {
        return controller;
    }

}
