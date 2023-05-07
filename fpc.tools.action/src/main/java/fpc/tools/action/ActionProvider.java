package fpc.tools.action;

import lombok.NonNull;

import java.util.Optional;

public interface ActionProvider {

    <A> Optional<? extends A> findAction(Class<A> actionType);

    default <A> A getAction(Class<A> actionType) {
        return findAction(actionType).orElseThrow(() -> new RuntimeException("Action not found '"+actionType+"'"));
    }
}
