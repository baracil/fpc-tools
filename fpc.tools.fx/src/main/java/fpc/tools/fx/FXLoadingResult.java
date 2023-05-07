package fpc.tools.fx;

import fpc.tools.lang.CastTool;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FXLoadingResult {

    private final Object controller;

    private final Object root;

    public <T> Optional<T> getController(Class<T> controllerType) {
        return CastTool.as(controllerType,controller);
    }

    public <T> Optional<T> getRoot(Class<T> rootType) {
        return CastTool.as(rootType,root);
    }

    public TypedFXLoadingResult<Object> asTyped() {
        return new TypedFXLoadingResult<>(controller,root);
    }

    public <C> Optional<TypedFXLoadingResult<C>> asTyped(Class<C> controllerType) {
        return getController(controllerType).map(c -> new TypedFXLoadingResult<>(c,root));
    }

    @SuppressWarnings("unchecked")
    public <T> T getRoot() {
        return (T)root;
    }

    @SuppressWarnings("unchecked")
    public <T> T getController() {
        return (T)controller;
    }

}
