package net.femtoparsec.tools.state;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UpdateResult<R,S> {

    @NonNull R oldRoot;

    @NonNull R newRoot;

    @NonNull S result;
}
