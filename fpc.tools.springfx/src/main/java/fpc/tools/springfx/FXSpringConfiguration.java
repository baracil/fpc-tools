package fpc.tools.springfx;

import fpc.tools.fp.UnaryOperator1;
import fpc.tools.fx.*;
import fpc.tools.fx.dialog.AlertShower;
import fpc.tools.fxmodel.FXViewProvider;
import fpc.tools.fxmodel.SlotMapperFactory;
import fpc.tools.i18n.Dictionary;
import fpc.tools.spring.ApplicationCloser;
import javafx.application.Platform;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@RequiredArgsConstructor
public class FXSpringConfiguration {

    public static final boolean FXLOADER_CACHE_ALL_LOADING = Boolean.getBoolean("fxloader.cache-all");

    private final ApplicationContext applicationContext;
    private final ApplicationArguments applicationArguments;

    private final Dictionary dictionary;

    @Bean
    @Qualifier("auto")
    public ApplicationCloser closer() {
        return new Closer()::close;
    }

    @Bean
    @Qualifier("auto")
    public FXLoaderFactory fxLoader() {
        return new FXLoaderFactoryWithControllerFactory(controllerFactory(), dictionary, createLoaderWrapper());
    }

    @Bean
    @Qualifier("auto")
    public SlotMapperFactory slotMapperFactory() {
        return new SpringSlotMapperFactory(applicationArguments);
    }

    @Bean
    @Qualifier("auto")
    public FXViewProvider fxViewProvider() {
        return new SPringFXViewProvider(applicationContext);
    }


    @Bean
    @Qualifier("auto")
    public ControllerFactory controllerFactory() {
        return new SpringControllerFactory(applicationContext);
    }

    @Bean
    @Qualifier("auto")
    public KeyTracker keyTracker() {
        return KeyTracker.create();
    }

    @Bean
    @Qualifier("auto")
    public AlertShower alertShower() {
        return AlertShower.create(dictionary);
    }

    @Bean
    @Qualifier("auto")
    public LocaleProperty localeProperty() {
        return new LocaleProperty();
    }

    @Bean
    @Qualifier("auto")
    public CursorSetter cursorSetter() {
        return new FXCursorSetter();
    }

    @Bean
    @Qualifier("auto")
    public FXDictionary fxDictionary() {
        return localeProperty().wrapDictionary(dictionary);
    }


    private UnaryOperator1<FXLoader> createLoaderWrapper() {
        if (FXLOADER_CACHE_ALL_LOADING) {
            return FXLoader::cached;
        }
        return fl -> fl;
    }


    private class Closer {

        private final AtomicBoolean alreadyClosed = new AtomicBoolean(false);

        public int close() {
            if (!alreadyClosed.getAndSet(true)) {
                Platform.exit();
                return SpringApplication.exit(applicationContext);
            }
            return -1;
        }
    }
}
