package fpc.tools.fx;

import java.net.URL;
import java.util.Objects;

public class URLGuesser {

    public static final String CONTROLLER_SUFFIX = "Controller";

    public URL guessFromController(Class<?> controllerType) {
        if (controllerType.getSimpleName().endsWith(CONTROLLER_SUFFIX)) {
            return extractFromClassName(controllerType);
        }
        throw new RuntimeException("Could not guess URL from "+controllerType);
    }

    private URL extractFromClassName(Class<?> controllerType) {
        final String className = controllerType.getSimpleName();
        final String resourceName = className.substring(0,className.length()-CONTROLLER_SUFFIX.length())+".fxml";
        final URL url = controllerType.getResource(resourceName);
        return Objects.requireNonNull(url,"Could not guess URL from "+controllerType+" : no resource found with name '"+resourceName+"'");
    }
}
