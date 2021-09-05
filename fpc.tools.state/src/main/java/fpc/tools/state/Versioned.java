package fpc.tools.state;

import java.util.Comparator;

public interface Versioned {

    Comparator<Versioned> VERSIONED_COMPARATOR = Comparator.comparingInt(Versioned::getVersion);

    int getVersion();
}
