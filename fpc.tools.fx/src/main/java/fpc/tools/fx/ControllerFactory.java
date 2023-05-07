package fpc.tools.fx;

import javafx.util.Callback;
import lombok.NonNull;

public interface ControllerFactory {

    Object getController(Class<?> controllerType) throws Exception;

    static ControllerFactory withNewInstance() {
        return controllerType -> controllerType.getDeclaredConstructor().newInstance();
    }


    default Callback<Class<?>,Object> asCallbackForFXMLLoader() {
        return c -> {
            try {
                return getController(c);
            } catch (Exception e) {
                throw new RuntimeException("Could not instantiate controller for class : " + c, e);
            }
        };
    }
}
