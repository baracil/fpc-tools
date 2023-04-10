package fpc.tools.lang;

import lombok.NonNull;

import java.util.Set;

public interface IdentifiedEnumWithAlternateIdentifications extends IdentifiedEnum {

    @NonNull
    Set<String> getAlternateIdentifications();
    
}
