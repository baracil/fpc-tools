package fpc.tools.state;

import lombok.NonNull;

public interface IdentityListener<R> {

    void stateChanged(@NonNull R oldValue, @NonNull R newValue);
}
