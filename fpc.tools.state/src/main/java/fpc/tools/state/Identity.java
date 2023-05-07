package fpc.tools.state;

import fpc.tools.fp.Function1;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

/**
 * @author Bastien Aracil
 */
public interface Identity<S> extends ReadOnlyIdentity<S>, IdentityMutator<S> {

    /**
     * @param initialState the initial state of this identity
     * @param <S> the type of the state
     * @return an new identity
     */
    static <S> Identity<S> create(S initialState) {
        return IdentityFactory.getInstance().createIdentity(initialState);
    }

    static <S> Identity<S> create(Function1<? super IdentityMutator<S>, ? extends S> initialStateFactory) {
        return IdentityFactory.getInstance().<S>createIdentity(initialStateFactory);
    }

    @Override
    default CompletionStage<S> getState() {
        return mutate(r -> r);
    }

}
