package net.femtoparsec.tools.lang;

import fpc.tools.lang.Caster;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class BasicCaster<T> implements Caster<T> {

    private final @NonNull Class<T> type;

    @Override
    public @NonNull Optional<T> cast(@NonNull Object object) {
        if (type.isInstance(object)) {
            return Optional.of(type.cast(object));
        }
        return Optional.empty();
    }
}
