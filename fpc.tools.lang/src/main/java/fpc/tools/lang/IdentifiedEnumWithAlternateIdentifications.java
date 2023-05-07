package fpc.tools.lang;

import java.util.Set;

public interface IdentifiedEnumWithAlternateIdentifications extends IdentifiedEnum {

    Set<String> getAlternateIdentifications();
    
}
