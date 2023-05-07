package net.femtoparsec.tools.lang;

import fpc.tools.lang.OSVersion;
import fpc.tools.lang.OSVersionFactory;
import lombok.NonNull;

import java.nio.file.Path;


public class FPCOSVersion implements OSVersion {

    public static OSVersionFactory provider() {
        return FPCOSVersion::new;
    }

    @Override
    public Path getUserHome() {
        return Path.of(System.getProperty("user.home"));
    }
}
