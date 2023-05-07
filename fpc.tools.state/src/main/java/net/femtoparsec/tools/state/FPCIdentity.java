package net.femtoparsec.tools.state;

import fpc.tools.fp.Function0;
import fpc.tools.fp.Function1;
import fpc.tools.fp.Function2;
import fpc.tools.lang.Listeners;
import fpc.tools.lang.Subscription;
import fpc.tools.lang.ThrowableTool;
import fpc.tools.state.Identity;
import fpc.tools.state.IdentityListener;
import fpc.tools.state.Mutation;
import javafx.beans.value.ObservableValue;
import lombok.NonNull;

import java.util.concurrent.*;

/**
 * @author Bastien Aracil
 */
public class FPCIdentity<R> implements Identity<R> {

    public static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private final Executor heavyActionExecutor;

    private volatile R rootState;

    private final Updater<R> updater;

    private final Listeners<IdentityListener<R>> listeners = Listeners.create();

    public FPCIdentity(R initialRootState, Updater<R> updater,
                       Executor heavyActionExecutor) {
        this.rootState = initialRootState;
        this.updater = updater;
        this.heavyActionExecutor = heavyActionExecutor;
    }

    public FPCIdentity(R initialRootState, Executor heavyActionExecutor) {
        this(initialRootState, new DefaultUpdater<>(), heavyActionExecutor);
    }

    public FPCIdentity(R initialRootState, Updater<R> updater) {
        this(initialRootState, updater, EXECUTOR_SERVICE);
    }

    public FPCIdentity(R initialRootState) {
        this(initialRootState, EXECUTOR_SERVICE);
    }

    @Override
    public CompletionStage<R> mutate(Mutation<R> mutation) {
        return updater.offerUpdatingOperation(
                mutation,
                this::getRootState,
                this::setRootState,
                (o,n) -> n
        ).thenApply(UpdateResult::getResult);
    }

    @Override
    public <V> CompletionStage<V> mutate(Mutation<R> mutation, Function1<? super R, ? extends CompletionStage<V>> action) {
        return updater.offerUpdatingOperation(
                mutation,
                this::getRootState,
                this::setRootState,
                (o,n) -> action.apply(n)
        ).thenCompose(UpdateResult::getResult);
    }

    @Override
    public <V> CompletionStage<V> mutate(Mutation<R> mutation, Function2<? super R, ? super R, ? extends CompletionStage<V>> action) {
        return updater.offerUpdatingOperation(
                mutation,
                this::getRootState,
                this::setRootState,
                action
        ).thenCompose(UpdateResult::getResult);
    }

    @Override
    public Subscription addListener(
            IdentityListener<R> listener
    ) {
        return listeners.addListener(listener);
    }

    @Override
    public void addWeakListener(IdentityListener<R> listener) {
        new WeakIdentityListener<>(this, listener);
    }

    @Override
    public <V> ObservableValue<V> asFXObservableValue(
            Function1<? super R, ? extends V> getter
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

    private R getRootState() {
        return this.rootState;
    }

    private void setRootState(R rootState) {
        final R oldState = this.rootState;
        this.rootState = rootState;
        if (oldState != rootState) {
            listeners.forEachListeners(IdentityListener::stateChanged, oldState, rootState);
        }
    }

    @Override
    public R getCurrentState() {
        return rootState;
    }

    private <P> CompletionStage<P> launchHeavyComputation(Function0<? extends P> heavyComputation) {
        final CompletableFuture<P> result = new CompletableFuture<>();

        heavyActionExecutor.execute(() -> {
            try {
                result.complete(heavyComputation.apply());
            } catch (Throwable t) {
                ThrowableTool.interruptIfCausedByInterruption(t);
                result.completeExceptionally(t);
            }
        });

        return result;

    }
}
