package fpc.tools.spring;

import java.util.Arrays;
import java.util.stream.Stream;

public interface SpringModule {

    Stream<String> packagesToScan();

    static SpringModule with(String... packagesToScan) {
        return () -> Arrays.stream(packagesToScan);
    }

    static SpringModule with(Class<?> basePackageClass) {
        return () -> Stream.of(basePackageClass.getPackage().getName());
    }
}
