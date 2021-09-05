package fpc.tools.fx;

import fpc.tools.i18n.Dictionary;
import lombok.NonNull;

import java.net.URL;

public interface FXLoaderFactory {

    /**
     * Create a FXLoader with the default dictionary
     * @param fxmlFile the url of the FXML file
     * @return a {@link FXLoader} that can be used to load the FXML file at the provided URL
     */
    @NonNull
    FXLoader create(@NonNull URL fxmlFile);

    /**
     * Create a FXLoader with the default dictionary
     * @param controllerClass the controller of the FXML file
     * @return a {@link FXLoader} that can be used to load the FXML file. The url of the file is guessed with from the controller class name (By removing Controller to its simple name)
     */
    @NonNull
    FXLoader create(@NonNull Class<?> controllerClass);

    /**
     * Create a FXLoader using a custom dictionary
     * @param fxmlFile the url of the FXML file
     * @return a {@link FXLoader} that can be used to load the FXML file at the provided URL
     */
    @NonNull
    FXLoader create(@NonNull URL fxmlFile, @NonNull Dictionary dictionary);

    /**
     * Create a FXLoader using a custom dictionary
     * @param controllerClass the controller of the FXML file
     * @return a {@link FXLoader} that can be used to load the FXML file. The url of the file is guessed with from the controller class name (By removing Controller to its simple name)
     */
    @NonNull
    FXLoader create(@NonNull Class<?> controllerClass, @NonNull Dictionary dictionary);


}
