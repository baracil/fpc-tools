package fpc.tools.lang;

import java.time.Clock;
import java.time.Instant;

public interface Instants {

    int VERSION = 1;

    Instant now();

    static Instants systemUTC() {
        return Clock.systemUTC()::instant;
    }
}
