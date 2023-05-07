package net.femtoparsec.tools.fx;

import fpc.tools.fx.ControllerFactory;
import fpc.tools.fx.FXLoader;
import fpc.tools.fx.FXLoadingResult;
import fpc.tools.i18n.Dictionary;
import javafx.fxml.FXMLLoader;
import javafx.util.BuilderFactory;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Locale;

@RequiredArgsConstructor
public class DefaultFXLoader implements FXLoader {

    private final ControllerFactory controllerFactory;

    private final BuilderFactory builderFactory;

    private final Dictionary dictionary;

    private final URL fxmlFile;

    @Override
    public FXLoadingResult load(Locale locale) {
        final FXMLLoader loader = createAndPrepareLoader(this.fxmlFile, locale);
        try {
            loader.load();
            return new FXLoadingResult(loader.getController(), loader.getRoot());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private FXMLLoader createAndPrepareLoader(URL fxmlFile, Locale locale) {
        final FXMLLoader loader = new FXMLLoader();
        loader.setLocation(fxmlFile);
        loader.setControllerFactory(controllerFactory.asCallbackForFXMLLoader());
        loader.setBuilderFactory(builderFactory);
        loader.setResources(dictionary.getResourceBundle(locale));
        return loader;
    }

    @Override
    public FXLoader cached() {
        return new CachedFXLoader(this);
    }
}
