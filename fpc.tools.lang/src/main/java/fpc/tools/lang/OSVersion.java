package fpc.tools.lang;

import lombok.NonNull;

import java.nio.file.Path;

public interface OSVersion {

    static @NonNull OSVersion create() {
        return OSVersionFactory.getInstance().create();
    }

    @NonNull Path getUserHome();
}
