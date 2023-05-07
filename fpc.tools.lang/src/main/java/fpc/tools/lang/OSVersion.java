package fpc.tools.lang;

import lombok.NonNull;

import java.nio.file.Path;

public interface OSVersion {

    static OSVersion create() {
        return OSVersionFactory.getInstance().create();
    }

    Path getUserHome();
}
