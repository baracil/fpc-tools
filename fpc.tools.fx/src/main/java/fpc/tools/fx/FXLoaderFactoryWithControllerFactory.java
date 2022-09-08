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

    @NonNull
    private final URLGuesser urlGuesser = new URLGuesser();

    @NonNull
    private final ControllerFactory controllerFactory;

    @NonNull
    private final BuilderFactory builderFactory;

    @NonNull
    private final Dictionary dictionary;

    @NonNull
    private final UnaryOperator1<FXLoader> fxLoaderWrapper;

    public FXLoaderFactoryWithControllerFactory(@NonNull ControllerFactory controllerFactory,
                                                @NonNull Dictionary dictionary,
                                                @NonNull UnaryOperator1<FXLoader> fxLoaderWrapper) {
        this(controllerFactory,new JavaFXBuilderFactory(),dictionary, fxLoaderWrapper);
    }

    @Override
    public @NonNull FXLoader create(@NonNull URL fxmlFile, @NonNull Dictionary dictionary) {
        return fxLoaderWrapper.apply(createRawLoader(fxmlFile,dictionary));
    }

    @Override
    public @NonNull FXLoader create(@NonNull Class<?> controllerClass, @NonNull Dictionary dictionary) {
        return create(urlGuesser.guessFromController(controllerClass),dictionary);
    }

    @Override
    public @NonNull FXLoader create(@NonNull URL fxmlFile) {
        return create(fxmlFile,dictionary);
    }

    @Override
    public @NonNull FXLoader create(@NonNull Class<?> controllerClass) {
        return create(controllerClass,dictionary);
    }


    private @NonNull FXLoader createRawLoader(@NonNull URL fxmlFile, @NonNull Dictionary dictionary) {
        return new FXLoaderSettingUserData(new DefaultFXLoader(controllerFactory, builderFactory, dictionary, fxmlFile), FXLoader.CONTROLLER_KEY);
    }
}
