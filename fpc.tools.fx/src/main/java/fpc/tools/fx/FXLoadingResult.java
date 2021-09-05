package fpc.tools.fx;

import fpc.tools.lang.CastTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FXLoadingResult {

    @NonNull
    private final Object controller;

    @NonNull
    private final Object root;

    @NonNull
    public <T> Optional<T> getController(@NonNull Class<T> controllerType) {
        return CastTool.as(controllerType,controller);
    }

    @NonNull
    public <T> Optional<T> getRoot(@NonNull Class<T> rootType) {
        return CastTool.as(rootType,root);
    }

    @NonNull
    public TypedFXLoadingResult<Object> asTyped() {
        return new TypedFXLoadingResult<>(controller,root);
    }

    @NonNull
    public <C> Optional<TypedFXLoadingResult<C>> asTyped(@NonNull Class<C> controllerType) {
        return getController(controllerType).map(c -> new TypedFXLoadingResult<>(c,root));
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public <T> T getRoot() {
        return (T)root;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public <T> T getController() {
        return (T)controller;
    }

}
