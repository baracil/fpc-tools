package fpc.tools.fx;

import javafx.scene.Node;
import lombok.NonNull;

import java.lang.ref.Reference;
import java.util.Locale;
import java.util.Optional;

public interface FXLoader {

    Object CONTROLLER_KEY = new Object();

    FXLoadingResult load(Locale locale);

    default FXLoadingResult load() {
        return load(Locale.getDefault());
    }

    FXLoader cached();

    static Optional<Object> retrieveController(Node node) {
        return Optional.ofNullable(node.getProperties().get(CONTROLLER_KEY))
                       .filter(Reference.class::isInstance)
                       .map(r -> ((Reference<?>) r).get());
    }

    static <T> Optional<T> retrieveController(Node node, Class<T> type) {
        return retrieveController(node).filter(type::isInstance).map(type::cast);
    }
}
