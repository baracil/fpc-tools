package net.femtoparsec.tools.state;

import fpc.tools.fp.Function0;
import fpc.tools.fp.Function1;
import fpc.tools.lang.Listeners;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.ThrowableTool;
import fpc.tools.state.Identity;
import fpc.tools.state.IdentityListener;
import fpc.tools.state.Mutation;
import javafx.beans.value.ObservableValue;
import lombok.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Bastien Aracil
 */
public class FPCIdentity<R> implements Identity<R> {

    @NonNull
    private final Executor heavyActionExecutor;

    @NonNull
    private volatile R rootState;

    @NonNull
    private final Updater<R> updater;

    private final Listeners<IdentityListener<R>> listeners = Listeners.create();

    public FPCIdentity(@NonNull R initialRootState, @NonNull Updater<R> updater,
                       @NonNull Executor heavyActionExecutor) {
        this.rootState = initialRootState;
        this.updater = updater;
        this.heavyActionExecutor = heavyActionExecutor;
    }

    public FPCIdentity(@NonNull R initialRootState, @NonNull Executor heavyActionExecutor) {
        this(initialRootState, new DefaultUpdater<>(), heavyActionExecutor);
    }

    public FPCIdentity(@NonNull R initialRootState, @NonNull Updater<R> updater) {
        this(initialRootState, updater, Executors.newCachedThreadPool());
    }

    public FPCIdentity(@NonNull R initialRootState) {
        this(initialRootState, Executors.newCachedThreadPool());
    }

    @Override
    public @NonNull CompletionStage<R> mutate(@NonNull Mutation<R> mutation) {
        return updater.offerUpdatingOperation(
                mutation,
                this::getRootState,
                this::setRootState,
                r -> r
        ).thenApply(UpdateResult::getResult);
    }

    @Override
    public @NonNull Subscription addListener(
            @NonNull IdentityListener<R> listener
    ) {
        return listeners.addListener(listener);
    }

    @Override
    public void addWeakListener(@NonNull IdentityListener<R> listener) {
        new WeakIdentityListener<>(this, listener);
    }

    @Override
    public @NonNull <V> ObservableValue<V> asFXObservableValue(
            @NonNull Function1<? super R, ? extends V> getter
    ) {
        final ObservableIdentityValue<R, V> observable = new ObservableIdentityValue<>(getRootState(), getter);
        addWeakListener(observable);
        return observable;
    }


    public void start() {
        updater.start();
    }

    public void stop() {
        updater.stop();
    }

    @NonNull
    private R getRootState() {
        return this.rootState;
    }

    private void setRootState(@NonNull R rootState) {
        final R oldState = this.rootState;
        this.rootState = rootState;
        if (oldState != rootState) {
            listeners.forEachListeners(IdentityListener::stateChanged, oldState, rootState);
        }
    }

    @Override
    public @NonNull R getCurrentState() {
        return rootState;
    }

    @NonNull
    private <P> CompletionStage<P> launchHeavyComputation(@NonNull Function0<? extends P> heavyComputation) {
        final CompletableFuture<P> result = new CompletableFuture<>();

        heavyActionExecutor.execute(() -> {
            try {
                result.complete(heavyComputation.f());
            } catch (Throwable t) {
                ThrowableTool.interruptIfCausedByInterruption(t);
                result.completeExceptionally(t);
            }
        });

        return result;

    }
}
