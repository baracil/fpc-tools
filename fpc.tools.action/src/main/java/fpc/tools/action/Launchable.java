package fpc.tools.action;

import lombok.NonNull;
import net.femtoparsec.tools.action.*;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * A {@link Launchable} is an objet that can be "launched" with the {@link ActionExecutor}.
 * Initially, <code>ActionExecutor</code> could only use classes of Action. This has been
 * extended to be able to launch a succession of actions (with {@link ActionChain}).
 *
 * @param <P> the type of the input parameter
 * @param <R> the type of the ouput parameter
 */
public interface Launchable<P,R>  {

    @NonNull
    Class<? extends Action<?,?>> getInitialAction();

    @NonNull
    CompletionStage<R> launch(@NonNull ActionExecutor executor, @NonNull P parameter);


    @NonNull
    static <P,R> Launchable<P,R> single(@NonNull Class<? extends Action<? super P,? extends R>> actionType) {
        return new Head<>(actionType);
    }

    @NonNull
    static <P,R> Launchable<P,R> singleAsync(@NonNull Class<? extends AsyncAction<? super P,? extends R>> actionType) {
        return new AsyncHead<>(actionType);
    }

    @NonNull
    static <P,R> ActionChain<P,R> start(@NonNull Class<? extends Action<P,R>> actionType) {
        return new Head<>(actionType);
    }

    @NonNull
    static <P,R> ActionChainOpt<P,R> startOpt(@NonNull Class<? extends Action<P, Optional<R>>> actionType) {
        return new HeadOpt<>(actionType);
    }

    @NonNull
    static <P,R> ActionChain<P,R> startStage(@NonNull Class<? extends Action<P,CompletionStage<R>>> actionType) {
        return new HeadStage<>(actionType);
    }

    @NonNull
    static <P,R> ActionChainOpt<P,R> startStageOpt(@NonNull Class<? extends Action<P, CompletionStage<Optional<R>>>> actionType) {
        return new HeadStageOpt<>(actionType);
    }
}
