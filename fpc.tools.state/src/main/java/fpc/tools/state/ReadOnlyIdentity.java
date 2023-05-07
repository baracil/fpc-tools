package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.lang.Subscription;
import javafx.beans.value.ObservableValue;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

/**
 * @author Bastien Aracil
 */
public interface ReadOnlyIdentity<S> {

    CompletionStage<S> getState();

    Subscription addListener(IdentityListener<S> listener);

    void addWeakListener(IdentityListener<S> listener);

    /**
     * @param getter a getter to extract a specific value from the root value of this identity
     * @param <V> the type of the return value
     * @return an observable value that is updated in the FX Thread (ths FX thread must be started before
     * calling this method)
     */
    <V> ObservableValue<V> asFXObservableValue(Function1<? super S,? extends V> getter);

    /**
     * @return an observable value that is updated in the FX Thread (ths FX thread must be started before
     * calling this method)
     */
    default ObservableValue<S> asFXObservableValue() {
        return asFXObservableValue(r -> r);
    }


    /**
     * @return the current state, there might be mutations in progress which means this state might not the up to date one
     */
    S getCurrentState();

}
