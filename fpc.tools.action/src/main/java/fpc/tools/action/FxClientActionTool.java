package fpc.tools.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.femtoparsec.tools.action.FPCActionExecutor;
import net.femtoparsec.tools.action.FPCActionManager;

import java.util.List;

@RequiredArgsConstructor
public class FxClientActionTool {

    @NonNull
    private final ActionStateProvider actionStateProvider;

    @NonNull
    private final Executor executor;

    @NonNull
    private final ActionProvider actionProvider;

    @NonNull
    private final List<ActionFilter> actionFilters;


    private ActionManager actionManager;

    private ActionExecutor actionExecutor;

    public ActionManager getActionManager() {
        if (actionManager == null) {
            actionManager = new FPCActionManager(actionStateProvider,getActionExecutor());
        }
        return actionManager;
    }

    public ActionExecutor getActionExecutor() {
        if (actionExecutor == null) {
            actionExecutor = new FPCActionExecutor(executor,actionProvider,actionFilters);
        }
        return actionExecutor;
    }

}
