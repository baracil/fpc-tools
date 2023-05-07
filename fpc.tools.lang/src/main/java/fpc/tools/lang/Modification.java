package fpc.tools.lang;

import lombok.Value;

public interface Modification<I,P> {

    I getId();
    P getOldValue();
    P getNewValue();

    default boolean doesNotChangeAnything() {
        return getNewValue().equals(getOldValue());
    }

    static <I,P> Modification<I,P> with(I id, P oldValue, P newValue) {
        return new Simple<>(id, oldValue, newValue);
    }

    @Value
    class Simple<I,P> implements Modification<I,P> {
        I id;
        P oldValue;
        P newValue;
    }
}
