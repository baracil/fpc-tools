package net.femtoparsec.tools.state;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Function0;
import fpc.tools.fp.Function1;
import fpc.tools.state.Mutation;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Bastien Aracil
 */
@RequiredArgsConstructor
public class Update<R, S> {

    @NonNull
    private final Function0<? extends R> rootStateGetter;

    @NonNull
    private final Consumer1<? super R> newRootStateConsumer;

    @NonNull
    @Getter
    private final Mutation<R> mutation;

    @NonNull
    private final Function1<? super R, ? extends S> getter;

    @NonNull
    public UpdateResult<R, S> performMutation() {
        final R currentState = rootStateGetter.get();
        final R newState = mutation.mutate(currentState);

        final UpdateResult<R, S> result = UpdateResult.<R, S>builder()
                                                      .oldRoot(currentState)
                                                      .newRoot(newState)
                                                      .result(getter.apply(newState))
                                                      .build();

        newRootStateConsumer.accept(newState);

        return result;
    }
}
