package fpc.tools.state;

import lombok.NonNull;

import java.util.Optional;

public interface RemoteDataBase<V> {

    boolean hasBeenLoaded();

    @NonNull
    Optional<V> getValue();

}
