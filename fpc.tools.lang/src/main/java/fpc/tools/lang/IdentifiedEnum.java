package fpc.tools.lang;

import lombok.NonNull;

public interface IdentifiedEnum extends Identified<String> {

    String getIdentification();

    String name();

    default boolean useNameForSerialization() {
        return false;
    }

}
