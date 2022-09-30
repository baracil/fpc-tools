package fpc.tools.lang;

import lombok.NonNull;

import java.time.Clock;
import java.time.Instant;

public interface Instants {

    int VERSION = 1;

    @NonNull Instant now();

    static @NonNull Instants systemUTC() {
        return Clock.systemUTC()::instant;
    }
}
