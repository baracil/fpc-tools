package net.femtoparsec.tools.state;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateResult<R,S> {

    R oldRoot;

    R newRoot;

    S result;
}
