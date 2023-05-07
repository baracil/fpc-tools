package fpc.tools.state;

import java.util.Optional;

public interface RemoteDataBase<V> {

    boolean hasBeenLoaded();

    Optional<V> getValue();

}
