package fpc.tools.lang;

public interface IdentifiedEnum extends Identified<String> {

    String getIdentification();

    String name();

    default boolean useNameForSerialization() {
        return false;
    }

}
