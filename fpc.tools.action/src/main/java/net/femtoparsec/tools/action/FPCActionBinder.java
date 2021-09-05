package net.femtoparsec.tools.action;

import fpc.tools.action.*;
import fpc.tools.fp.Consumer0;
import fpc.tools.fp.Function0;
import fpc.tools.lang.ThrowableTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FPCActionBinder<P,R> implements ActionBinder<P> {

    @NonNull
    private final ActionStateProvider actionStateProvider;

    @NonNull
    private final ActionExecutor actionExecutor;

    @NonNull
    private final Launchable<P,R> launchable;

    private final ItemInfoProvider itemInfoProvider = new ItemInfoProvider();

    @Override
    public @NonNull ActionBinding createBinding(@NonNull Object item,
                                                @NonNull Function0<? extends Optional<? extends P>> parameterSupplier) {
        final ItemInfo itemInfo = itemInfoProvider.createInfo(item);
        final Consumer0 executable = createExecutable(parameterSupplier);
        return new FPCActionBinding(itemInfo,executable, actionStateProvider.disabledBinding(launchable.getInitialAction()));
    }

    @NonNull
    private Consumer0 createExecutable(@NonNull Function0<? extends Optional<? extends P>> parameterSupplier) {
        return () -> {
            try {
                parameterSupplier.get().ifPresent(p -> launchable.launch(actionExecutor, p));
            } catch (Throwable t) {
                ThrowableTool.interruptIfCausedByInterruption(t);
            }
        };
    }
}
