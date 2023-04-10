package fpc.tools.lang;

import lombok.NonNull;

public interface IdentifiedEnum extends Identified<String> {

    @NonNull
    String getIdentification();

    @NonNull String name();

    default boolean useNameForSerialization() {
        return false;
    }

}
