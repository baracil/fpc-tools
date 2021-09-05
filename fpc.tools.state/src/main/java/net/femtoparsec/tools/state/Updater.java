package net.femtoparsec.tools.state;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Function0;
import fpc.tools.fp.Function1;
import fpc.tools.state.Mutation;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

/**
 * @author Bastien Aracil
 */
public interface Updater<R> {

    void start();

    void stop();

    @NonNull
    <S> CompletionStage<UpdateResult<R,S>> offerUpdatingOperation(
            @NonNull Mutation<R> mutation,
            @NonNull Function0<? extends R> rootStateGetter,
            @NonNull Consumer1<? super R> newRootStateConsumer,
            @NonNull Function1<? super R, ? extends S> subStateGetter
            );


}
