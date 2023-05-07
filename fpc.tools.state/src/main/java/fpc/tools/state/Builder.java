package fpc.tools.state;

import lombok.NonNull;

public interface Builder<S,B> {

    B toBuilder(S state);

    S build(B builder);
}
