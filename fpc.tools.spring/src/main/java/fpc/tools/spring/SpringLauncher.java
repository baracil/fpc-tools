package fpc.tools.spring;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Predicate1;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
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

    private final List<String> arguments;

    private final Class<?>[] applicationClasses;

    private final Consumer1<SpringApplication> modifier;

    private final ApplicationContextInitializer<?>[] initializers;

    private final Predicate1<? super SpringModule> springModuleFilter;


    public SpringLauncher(List<String> arguments,
                          Class<?>[] applicationClasses,
                          ApplicationContextInitializer<?>[] initializers,
                          Predicate1<? super SpringModule> springModuleFilter) {
        this(arguments,applicationClasses,a -> {}, initializers, springModuleFilter);
   }
    public SpringLauncher(List<String> arguments,
                          Class<?> applicationClass,
                          ApplicationContextInitializer<?>[] initializers,
                          Predicate1<? super SpringModule> springModuleFilter) {
        this(arguments,new Class<?>[]{applicationClass},a -> {}, initializers, springModuleFilter);
   }

    public SpringLauncher(List<String> arguments,
                          Class<?> applicationClass,
                          Consumer1<SpringApplication> modifier,
                          ApplicationContextInitializer<?>[] initializers,
                          Predicate1<? super SpringModule> springModuleFilter) {
        this(arguments,new Class<?>[]{applicationClass},modifier, initializers, springModuleFilter);
   }

    public ApplicationCloser launch() {
        return new Execution().launch();
    }

    private class Execution {

        private @Nullable SpringApplication application;
        private @Nullable String[] extraPackagesToScan;


        private ApplicationCloser launch() {
            this.createSpringApplication();
            this.retrieveAllExtraPackagesToScan();
            this.setupSpringApplicationInitializerToTakeIntoAccountExtraPackages();
            return this.launchTheApplicationAndConstructTheCloser();
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
            assert extraPackagesToScan != null;
            assert application != null;
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

        private ApplicationCloser launchTheApplicationAndConstructTheCloser() {
            assert application != null;
            final ApplicationContext app = application.run(arguments.toArray(String[]::new));
            return () -> SpringApplication.exit(app);
        }

    }

}
