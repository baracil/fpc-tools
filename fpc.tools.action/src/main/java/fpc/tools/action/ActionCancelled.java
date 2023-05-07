package fpc.tools.action;

import fpc.tools.lang.FPCException;
import lombok.Getter;

public class ActionCancelled extends FPCException {

    @Getter
    private final Class<? extends Action> actionType;

    public ActionCancelled(Class<? extends Action> actionType) {
        super("Action has been cancelled : "+actionType);
        this.actionType = actionType;
    }
}
