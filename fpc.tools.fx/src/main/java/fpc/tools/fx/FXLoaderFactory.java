package fpc.tools.fx;

import fpc.tools.i18n.Dictionary;

import java.net.URL;

public interface FXLoaderFactory {

    /**
     * Create a FXLoader with the default dictionary
     * @param fxmlFile the url of the FXML file
     * @return a {@link FXLoader} that can be used to load the FXML file at the provided URL
     */
    FXLoader create(URL fxmlFile);

    /**
     * Create a FXLoader with the default dictionary
     * @param controllerClass the controller of the FXML file
     * @return a {@link FXLoader} that can be used to load the FXML file. The url of the file is guessed with from the controller class name (By removing Controller to its simple name)
     */
    FXLoader create(Class<?> controllerClass);

    /**
     * Create a FXLoader using a custom dictionary
     * @param fxmlFile the url of the FXML file
     * @return a {@link FXLoader} that can be used to load the FXML file at the provided URL
     */
    FXLoader create(URL fxmlFile, Dictionary dictionary);

    /**
     * Create a FXLoader using a custom dictionary
     * @param controllerClass the controller of the FXML file
     * @return a {@link FXLoader} that can be used to load the FXML file. The url of the file is guessed with from the controller class name (By removing Controller to its simple name)
     */
    FXLoader create(Class<?> controllerClass, Dictionary dictionary);


}
