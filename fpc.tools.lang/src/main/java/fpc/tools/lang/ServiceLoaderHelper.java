package fpc.tools.lang;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.util.Comparator;
import java.util.ServiceLoader;

public class ServiceLoaderHelper {

    @NonNull
    public static <S> S load(@NonNull ServiceLoader<S> serviceLoader) {
        return serviceLoader.stream()
                            .max(Comparator.comparing(ServiceLoaderHelper::getProviderPriority))
                            .map(ServiceLoader.Provider::get)
                            .orElseThrow(() -> new IllegalStateException("Could not find any provider with " + serviceLoader));
    }

    private static int getProviderPriority(@NonNull ServiceLoader.Provider<?> provider) {
        final Priority priority = provider.type().getAnnotation(Priority.class);
        return priority == null ? Integer.MIN_VALUE : priority.value();
    }

    public static <S> @NonNull ImmutableList<S> loadAll(@NonNull ServiceLoader<S> serviceLoader) {
        return serviceLoader.stream()
                            .sorted(Comparator.comparing(ServiceLoaderHelper::getProviderPriority))
                            .map(ServiceLoader.Provider::get)
                            .collect(ImmutableList.toImmutableList());
    }
}
