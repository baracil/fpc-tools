package fpc.tools.lang;

import java.util.Comparator;
import java.util.List;
import java.util.ServiceLoader;

public class ServiceLoaderHelper {

    public static <S> S load(ServiceLoader<S> serviceLoader) {
        return serviceLoader.stream()
                            .max(Comparator.comparing(ServiceLoaderHelper::getProviderPriority))
                            .map(ServiceLoader.Provider::get)
                            .orElseThrow(() -> new IllegalStateException("Could not find any provider with " + serviceLoader));
    }

    private static int getProviderPriority(ServiceLoader.Provider<?> provider) {
        final Priority priority = provider.type().getAnnotation(Priority.class);
        return priority == null ? Integer.MIN_VALUE : priority.value();
    }

    public static <S> List<S> loadAll(ServiceLoader<S> serviceLoader) {
        return serviceLoader.stream()
                            .sorted(Comparator.comparing(ServiceLoaderHelper::getProviderPriority))
                            .map(ServiceLoader.Provider::get)
                            .toList();
    }
}
