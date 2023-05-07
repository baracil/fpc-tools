package net.femtoparsec.tools.state;

import fpc.tools.state.RemoteData;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.util.Optional;

@RequiredArgsConstructor
public class FPCRemoteData<V> implements RemoteData<V> {

    private static final FPCRemoteData NOT_LOADED = new FPCRemoteData<>(null, RemoteDataState.NOT_LOADED);
    private static final FPCRemoteData LOADED_NOT_FOUND = new FPCRemoteData<>(null, RemoteDataState.LOADED_NOT_FOUND);

    @SuppressWarnings("unchecked")
    public static <V> FPCRemoteData<V> notLoaded() {
        return NOT_LOADED;
    }

    @SuppressWarnings("unchecked")
    public static <V> FPCRemoteData<V> loadedNotFound() {
        return LOADED_NOT_FOUND;
    }

    public static <V> FPCRemoteData<V> loadedFound(V value) {
        return new FPCRemoteData<>(value, RemoteDataState.LOADED);
    }

    private @Nullable final V value;

    private final RemoteDataState state;

    @Override
    public boolean hasBeenLoaded() {
        return switch (state) {
            case LOADED_NOT_FOUND, LOADED -> true;
            default -> false;
        };
    }

    @Override
    public Optional<V> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public RemoteData<V> reset() {
        return notLoaded();
    }

    @Override
    public RemoteData<V> loadedAndNotFound() {
        return loadedNotFound();
    }

    @Override
    public RemoteData<V> loadedAndFound(V value) {
        return loadedFound(value);
    }
}

