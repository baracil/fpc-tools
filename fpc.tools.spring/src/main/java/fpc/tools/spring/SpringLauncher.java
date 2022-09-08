package fpc.tools.spring;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Predicate1;
import fpc.tools.fp.UnaryOperator1;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotationConfigRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author Bastien Aracil
 */
@Slf4j
@RequiredArgsConstructor
public class SpringLauncher {

    @NonNull
    private final List<String> arguments;

    @NonNull
    private final Class<?>[] applicationClasses;

    @NonNull
    private final Consumer1<SpringApplication> modifier;

    @NonNull
    private final ApplicationContextInitializer<?>[] initializers;

    @NonNull
    private final Predicate1<? super SpringModule> springModuleFilter;


    public SpringLauncher(@NonNull List<String> arguments,
                          @NonNull Class<?>[] applicationClasses,
                          @NonNull ApplicationContextInitializer<?>[] initializers,
                          @NonNull Predicate1<? super SpringModule> springModuleFilter) {
        this(arguments,applicationClasses,a -> {}, initializers, springModuleFilter);
   }
    public SpringLauncher(@NonNull List<String> arguments,
                          @NonNull Class<?> applicationClass,
                          @NonNull ApplicationContextInitializer<?>[] initializers,
                          @NonNull Predicate1<? super SpringModule> springModuleFilter) {
        this(arguments,new Class<?>[]{applicationClass},a -> {}, initializers, springModuleFilter);
   }

    public SpringLauncher(@NonNull List<String> arguments,
                          @NonNull Class<?> applicationClass,
                          @NonNull Consumer1<SpringApplication> modifier,
                          @NonNull ApplicationContextInitializer<?>[] initializers,
                          @NonNull Predicate1<? super SpringModule> springModuleFilter) {
        this(arguments,new Class<?>[]{applicationClass},modifier, initializers, springModuleFilter);
   }

    @NonNull
    public ApplicationCloser launch() {
        return new Execution().launch();
    }

    private class Execution {

        private SpringApplication application;

        private String[] extraPackagesToScan;

        private ApplicationCloser closer;

        private ApplicationCloser launch() {
            this.createSpringApplication();
            this.retrieveAllExtraPackagesToScan();
            this.setupSpringApplicationInitializerToTakeIntoAccountExtraPackages();
            this.launchTheApplicationAndConstructTheCloser();
            return closer;
        }


        private void createSpringApplication() {
            application = new SpringApplication(applicationClasses);
            application.setBannerMode(Banner.Mode.OFF);
            application.addInitializers(initializers);
            modifier.accept(application);
        }

        private void retrieveAllExtraPackagesToScan() {
            extraPackagesToScan = ServiceLoader.load(SpringModule.class)
                                               .stream()
                                               .map(ServiceLoader.Provider::get)
                                               .filter(springModuleFilter)
                                               .flatMap(SpringModule::packagesToScan)
                                               .toArray(String[]::new);
            if (extraPackagesToScan.length == 0) {
                LOG.warn("No extra package found");
            }
            Arrays.stream(extraPackagesToScan).forEach(s -> LOG.info("  Extra : {}", s));
        }

        private void setupSpringApplicationInitializerToTakeIntoAccountExtraPackages() {
            if (extraPackagesToScan.length == 0) {
                return;
            }
            application.addInitializers(c -> {
                if (c instanceof AnnotationConfigRegistry) {
                    ((AnnotationConfigRegistry) c).scan(extraPackagesToScan);
                } else {
                    LOG.error(
                            "Could not add extra package to scans. Context is not an AnnotationConfigRegistry : context.class={}",
                            c.getClass());
                }
            });
        }

        private void launchTheApplicationAndConstructTheCloser() {
            final ApplicationContext app = application.run(arguments.toArray(String[]::new));
            closer = () -> SpringApplication.exit(app);
        }

    }

}
