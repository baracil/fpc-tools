package fpc.tools.state;

import net.femtoparsec.tools.state.FPCRemoteData;

/**
 * A structure to handle remote data obtained asynchronously.
 * For the moment, this feature is not used (only the {@link #loaded(Object)} method is used) and moreover
 * a loading state should be also implemented for a complete asynchronous data structure.
 *
 * @param <V> the type of the remote data
 */
public interface RemoteData<V> extends RemoteDataBase<V> {

    static <V> RemoteData<V> notLoaded() {
        return FPCRemoteData.notLoaded();
    }

    static <V> RemoteData<V> loaded(V value) {
        return FPCRemoteData.loadedFound(value);
    }

    RemoteData<V> reset();

    RemoteData<V> loadedAndNotFound();

    RemoteData<V> loadedAndFound(V value);
}
