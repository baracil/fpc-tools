package fpc.tools.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.femtoparsec.tools.action.FPCActionExecutor;
import net.femtoparsec.tools.action.FPCActionManager;

import java.util.List;

@RequiredArgsConstructor
public class FxClientActionTool {

    private final ActionStateProvider actionStateProvider;

    private final Executor executor;

    private final ActionProvider actionProvider;

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
