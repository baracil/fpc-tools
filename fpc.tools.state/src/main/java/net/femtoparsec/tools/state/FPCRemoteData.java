package net.femtoparsec.tools.state;

import fpc.tools.state.RemoteData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FPCRemoteData<V> implements RemoteData<V> {

    private static final FPCRemoteData NOT_LOADED = new FPCRemoteData<>(null, RemoteDataState.NOT_LOADED);
    private static final FPCRemoteData LOADED_NOT_FOUND = new FPCRemoteData<>(null, RemoteDataState.LOADED_NOT_FOUND);

    @SuppressWarnings("unchecked")
    @NonNull
    public static <V> FPCRemoteData<V> notLoaded() {
        return NOT_LOADED;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public static <V> FPCRemoteData<V> loadedNotFound() {
        return LOADED_NOT_FOUND;
    }

    @NonNull
    public static <V> FPCRemoteData<V> loadedFound(@NonNull V value) {
        return new FPCRemoteData<>(value, RemoteDataState.LOADED);
    }

    private final V value;

    private final RemoteDataState state;

    @Override
    public boolean hasBeenLoaded() {
        return switch (state) {
            case LOADED_NOT_FOUND, LOADED -> true;
            default -> false;
        };
    }

    @Override
    public @NonNull Optional<V> getValue() {
        return Optional.ofNullable(value);
    }

    @Override
    public @NonNull RemoteData<V> reset() {
        return notLoaded();
    }

    @Override
    public @NonNull RemoteData<V> loadedAndNotFound() {
        return loadedNotFound();
    }

    @Override
    public @NonNull RemoteData<V> loadedAndFound(@NonNull V value) {
        return loadedFound(value);
    }
}

