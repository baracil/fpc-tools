package fpc.tools.fx.dialog;

import lombok.NonNull;

public interface DialogResultHandler<O> {

    default void onCancelled() {}

    default void onApplied(O value) {}

    default void onValidated(O value) {}

}
