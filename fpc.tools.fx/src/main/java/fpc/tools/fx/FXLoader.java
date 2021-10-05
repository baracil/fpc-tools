package fpc.tools.fx;

import javafx.scene.Node;
import lombok.NonNull;

import java.lang.ref.Reference;
import java.util.Locale;
import java.util.Optional;

public interface FXLoader {

    Object CONTROLLER_KEY = new Object();

    @NonNull
    FXLoadingResult load(@NonNull Locale locale);

    default FXLoadingResult load() {
        return load(Locale.getDefault());
    }

    @NonNull
    FXLoader cached();

    static @NonNull Optional<Object> retrieveController(@NonNull Node node) {
        return Optional.ofNullable(node.getProperties().get(CONTROLLER_KEY))
                       .filter(Reference.class::isInstance)
                       .map(r -> ((Reference<?>) r).get());
    }

    static @NonNull <T> Optional<T> retrieveController(@NonNull Node node, @NonNull Class<T> type) {
        return retrieveController(node).filter(type::isInstance).map(type::cast);
    }
}
