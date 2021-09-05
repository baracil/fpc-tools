package fpc.tools.spring;

import lombok.NonNull;

import java.util.Arrays;
import java.util.stream.Stream;

public interface SpringModule {

    @NonNull Stream<String> packagesToScan();

    static @NonNull SpringModule with(@NonNull String... packagesToScan) {
        return () -> Arrays.stream(packagesToScan);
    }

    static @NonNull SpringModule with(@NonNull Class<?> basePackageClass) {
        return () -> Stream.of(basePackageClass.getPackage().getName());
    }
}
