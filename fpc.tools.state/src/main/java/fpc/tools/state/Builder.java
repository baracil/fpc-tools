package fpc.tools.state;

import lombok.NonNull;

public interface Builder<S,B> {

    @NonNull
    B toBuilder(@NonNull S state);

    @NonNull
    S build(@NonNull B builder);
}
