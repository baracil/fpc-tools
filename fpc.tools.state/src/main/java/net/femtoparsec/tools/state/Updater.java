package net.femtoparsec.tools.state;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Function0;
import fpc.tools.fp.Function2;
import fpc.tools.state.Mutation;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

/**
 * @author Bastien Aracil
 */
public interface Updater<R> {

    void start();

    void stop();

    <S> CompletionStage<UpdateResult<R,S>> offerUpdatingOperation(
            Mutation<R> mutation,
            Function0<? extends R> rootStateGetter,
            Consumer1<? super R> newRootStateConsumer,
            Function2<? super R, ? super R, ? extends S> subStateGetter
            );


}
