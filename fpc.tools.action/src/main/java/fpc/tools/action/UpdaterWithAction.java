package fpc.tools.action;

import fpc.tools.fp.Function1;
import fpc.tools.lang.Modification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class UpdaterWithAction<I, T> {

    /**
     * Use inner class to hide the parameter type of P
     * only used internally
     */
    @NonNull
    private final Inner<?> inner;

    public <P> UpdaterWithAction(
            @NonNull ActionLauncher actionLauncher,
            @NonNull Class<? extends Action<P,?>> action,
            @NonNull Function1<? super Modification<I, T>, ? extends P> parameterFactory
    ) {
        this.inner = new Inner<>(actionLauncher, Launchable.single(action), parameterFactory);
    }

    public <P> UpdaterWithAction(
            @NonNull ActionLauncher actionLauncher,
            @NonNull Launchable<P,?> launchable,
            @NonNull Function1<? super Modification<I, T>, ? extends P> parameterFactory
    ) {
        this.inner = new Inner<>(actionLauncher, launchable, parameterFactory);
    }

    public void handle(@NonNull Modification<I, T> modification) {
        if (modification.doesNotChangeAnything()) {
            return;
        }
        inner.pushAction(modification);
    }


    @RequiredArgsConstructor
    private class Inner<P> {

        @NonNull
        private final ActionLauncher actionLauncher;

        private final Launchable<P,?> launchable;

        @NonNull
        private final Function1<? super Modification<I, T>, ? extends P> parameterFactory;

        public ActionTicket<?> pushAction(@NonNull Modification<I, T> modification) {
            final P parameter = parameterFactory.apply(modification);
            return actionLauncher.pushAction(launchable, parameter);
        }

    }
}
