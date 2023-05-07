package fpc.tools.action;

import fpc.tools.state.SetState;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

/**
 * Contains the state of each action : is an action disabled or enabled
 */
@RequiredArgsConstructor
public class ActionState {


    public static ActionState allEnabled() {
        return new ActionState(SetState.empty());
    }


    private final SetState<Class<? extends Action<?,?>>> disabledActions;


    public boolean isEnabled(Class<? extends Action<?,?>> action) {
        return !isDisabled(action);
    }

    public boolean isDisabled(Class<? extends Action<?, ?>> action) {
        return disabledActions.contains(action);
    }


    public Builder toBuilder() {
        return new Builder(disabledActions.toBuilder());
    }


    @RequiredArgsConstructor
    private static class Builder {

        private final SetState.Builder<Class<? extends Action<?,?>>> disableActionBuilder;

        public Builder enableAction(Class<? extends Action<?,?>> action) {
            disableActionBuilder.remove(action);
            return this;
        }

        public Builder enableActions(Collection<Class<? extends Action<?,?>>> action) {
            action.forEach(disableActionBuilder::remove);
            return this;
        }

        public Builder disableAction(Class<? extends Action<?,?>> action) {
            disableActionBuilder.add(action);
            return this;
        }

        public Builder disableActions(Collection<Class<? extends Action<?,?>>> action) {
            action.forEach(disableActionBuilder::add);
            return this;
        }

    }



}
