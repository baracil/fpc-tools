package net.femtoparsec.tools.state;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Function0;
import fpc.tools.fp.Function2;
import fpc.tools.state.Mutation;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Bastien Aracil
 */
@RequiredArgsConstructor
public class Update<R, S> {

    private final Function0<? extends R> rootStateGetter;

    private final Consumer1<? super R> newRootStateConsumer;

    @Getter
    private final Mutation<R> mutation;

    private final Function2<? super R, ? super R, ? extends S> getter;

    public UpdateResult<R, S> performMutation() {
        final R currentState = rootStateGetter.get();
        final R newState = mutation.mutate(currentState);

        final UpdateResult<R, S> result = UpdateResult.<R, S>builder()
                                                      .oldRoot(currentState)
                                                      .newRoot(newState)
                                                      .result(getter.apply(currentState,newState))
                                                      .build();

        newRootStateConsumer.accept(newState);

        return result;
    }
}
