package fpc.tools.lang;

import lombok.NonNull;

import java.util.Set;

public interface IdentifiedEnumWithAlternateIdentifications extends IdentifiedEnum {

    Set<String> getAlternateIdentifications();
    
}
