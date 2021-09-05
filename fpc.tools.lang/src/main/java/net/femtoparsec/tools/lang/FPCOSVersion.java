package net.femtoparsec.tools.lang;

import fpc.tools.lang.OSVersion;
import fpc.tools.lang.OSVersionFactory;
import lombok.NonNull;

import java.nio.file.Path;


public class FPCOSVersion implements OSVersion {

    public static @NonNull OSVersionFactory provider() {
        return FPCOSVersion::new;
    }

    @Override
    public @NonNull Path getUserHome() {
        return Path.of(System.getProperty("user.home"));
    }
}
