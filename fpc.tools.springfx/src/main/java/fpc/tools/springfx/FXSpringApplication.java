package fpc.tools.springfx;

import fpc.tools.fp.TryResult;
import fpc.tools.fx.FXProperties;
import fpc.tools.spring.AddSingletonToApplicationContext;
import fpc.tools.spring.ApplicationCloser;
import fpc.tools.spring.SpringLauncher;
import fpc.tools.spring.SpringModule;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContextInitializer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Log4j2
@RequiredArgsConstructor
public abstract class FXSpringApplication extends Application {

    @NonNull
    private final Class<?> applicationClass;

    private ApplicationCloser contextCloser = () -> {
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            beforeLaunchingSpring(primaryStage);
            this.launchSpring(new AddSingletonToApplicationContext("fxProperties", createFxProperties(primaryStage)))
                .whenComplete((r, t) -> handleSpringLaunchCompletion(primaryStage, r, t));
        } catch (Throwable e) {
            LOG.error("Launch failed ", e);
            try {
                contextCloser.execute();
            } catch (Throwable se) {
                e.addSuppressed(se);
            }
            throw e;
        }
    }


    @NonNull
    private FXProperties createFxProperties(Stage primaryStage) {
        return new FXProperties(primaryStage, getHostServices());
    }

    private void handleSpringLaunchCompletion(@NonNull Stage primaryStage, ApplicationCloser applicationCloser, Throwable error) {
        final TryResult<ApplicationCloser, Throwable> result = error == null ? TryResult.success(applicationCloser) : TryResult.failure(error);
        this.afterLaunchingSpring(primaryStage, result);

        result.ifFailedAccept(e -> {
            LOG.error("Launch failed ", e);
            Platform.exit();
            System.exit(1);
        });

        this.contextCloser = result.getResult(this.contextCloser);
    }


    @NonNull
    private CompletionStage<ApplicationCloser> launchSpring(
            ApplicationContextInitializer<?>... initializers) {
        final var springLauncher = new SpringLauncher(getParameters().getRaw(),
                prepareApplicationClass(),
                initializers,
                this::processPackagesBeforeIncludingThemIntoSpring);
        return CompletableFuture.supplyAsync(springLauncher::launch);
    }

    private Class<?>[] prepareApplicationClass() {
        if (useFXSpringConfiguration()) {
            return new Class<?>[]{applicationClass, FXSpringConfiguration.class};
        }
        return new Class<?>[]{applicationClass};
    }

    protected boolean processPackagesBeforeIncludingThemIntoSpring(@NonNull SpringModule plugin) {
        return true;
    }

    protected boolean useFXSpringConfiguration() {
        return true;
    }

    protected abstract void beforeLaunchingSpring(@NonNull Stage primaryStage) throws Exception;

    protected void afterLaunchingSpring(
            @NonNull Stage primaryStage,
            @NonNull TryResult<ApplicationCloser, Throwable> result) {
    }
}
