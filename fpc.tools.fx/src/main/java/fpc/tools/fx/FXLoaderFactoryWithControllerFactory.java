package fpc.tools.fx;

import fpc.tools.fp.UnaryOperator1;
import fpc.tools.i18n.Dictionary;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.BuilderFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.femtoparsec.tools.fx.DefaultFXLoader;
import net.femtoparsec.tools.fx.FXLoaderSettingUserData;

import java.net.URL;

@RequiredArgsConstructor
public class FXLoaderFactoryWithControllerFactory implements FXLoaderFactory {

    private final URLGuesser urlGuesser = new URLGuesser();

    private final ControllerFactory controllerFactory;

    private final BuilderFactory builderFactory;

    private final Dictionary dictionary;

    private final UnaryOperator1<FXLoader> fxLoaderWrapper;

    public FXLoaderFactoryWithControllerFactory(ControllerFactory controllerFactory,
                                                Dictionary dictionary,
                                                UnaryOperator1<FXLoader> fxLoaderWrapper) {
        this(controllerFactory,new JavaFXBuilderFactory(),dictionary, fxLoaderWrapper);
    }

    @Override
    public FXLoader create(URL fxmlFile, Dictionary dictionary) {
        return fxLoaderWrapper.apply(createRawLoader(fxmlFile,dictionary));
    }

    @Override
    public FXLoader create(Class<?> controllerClass, Dictionary dictionary) {
        return create(urlGuesser.guessFromController(controllerClass),dictionary);
    }

    @Override
    public FXLoader create(URL fxmlFile) {
        return create(fxmlFile,dictionary);
    }

    @Override
    public FXLoader create(Class<?> controllerClass) {
        return create(controllerClass,dictionary);
    }


    private FXLoader createRawLoader(URL fxmlFile, Dictionary dictionary) {
        return new FXLoaderSettingUserData(new DefaultFXLoader(controllerFactory, builderFactory, dictionary, fxmlFile), FXLoader.CONTROLLER_KEY);
    }
}
