package fpc.tools.action;

import fpc.tools.lang.FPCException;
import lombok.Getter;
import lombok.NonNull;

public class ActionCancelled extends FPCException {

    @NonNull
    @Getter
    private final Class<? extends Action> actionType;

    public ActionCancelled(@NonNull Class<? extends Action> actionType) {
        super("Action has been cancelled : "+actionType);
        this.actionType = actionType;
    }
}
