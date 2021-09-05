package fpc.tools.lang;

import lombok.NonNull;
import lombok.Value;

public interface Modification<I,P> {

    @NonNull I getId();
    @NonNull P getOldValue();
    @NonNull P getNewValue();

    default boolean doesNotChangeAnything() {
        return getNewValue().equals(getOldValue());
    }

    static <I,P> Modification<I,P> with(@NonNull I id, @NonNull P oldValue, @NonNull P newValue) {
        return new Simple<>(id, oldValue, newValue);
    }

    @Value
    class Simple<I,P> implements Modification<I,P> {
        @NonNull I id;
        @NonNull P oldValue;
        @NonNull P newValue;
    }
}
