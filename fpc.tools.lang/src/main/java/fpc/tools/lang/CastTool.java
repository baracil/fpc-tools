package fpc.tools.lang;

import lombok.NonNull;
import net.femtoparsec.tools.lang.BasicCaster;

import java.util.Optional;

public class CastTool {

    @NonNull
    public static <T> Caster<T> caster(@NonNull Class<T> type) {
        return new BasicCaster<>(type);
    }

    @NonNull
    public static <T> Optional<T> as(@NonNull Class<T> type, @NonNull Object value) {
        return caster(type).cast(value);
    }
}
