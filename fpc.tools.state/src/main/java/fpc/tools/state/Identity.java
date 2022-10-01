package fpc.tools.state;

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
    static <S> Identity<S> create(@NonNull S initialState) {
        return IdentityFactory.getInstance().createIdentity(initialState);
    }

    @Override
    @NonNull
    default CompletionStage<S> getState() {
        return mutate(r -> r);
    }

}
