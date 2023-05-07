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

    private final ActionStateProvider actionStateProvider;

    private final ActionExecutor actionExecutor;

    private final Launchable<P,R> launchable;

    private final ItemInfoProvider itemInfoProvider = new ItemInfoProvider();

    @Override
    public ActionBinding createBinding(Object item,
                                                Function0<? extends Optional<? extends P>> parameterSupplier) {
        final ItemInfo itemInfo = itemInfoProvider.createInfo(item);
        final Consumer0 executable = createExecutable(parameterSupplier);
        return new FPCActionBinding(itemInfo,executable, actionStateProvider.disabledBinding(launchable.getInitialAction()));
    }

    private Consumer0 createExecutable(Function0<? extends Optional<? extends P>> parameterSupplier) {
        return () -> {
            try {
                parameterSupplier.get().ifPresent(p -> launchable.launch(actionExecutor, p));
            } catch (Throwable t) {
                ThrowableTool.interruptIfCausedByInterruption(t);
            }
        };
    }
}
