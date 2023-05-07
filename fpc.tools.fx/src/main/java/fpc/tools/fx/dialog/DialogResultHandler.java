package fpc.tools.fx.dialog;

public interface DialogResultHandler<O> {

    default void onCancelled() {}

    default void onApplied(O value) {}

    default void onValidated(O value) {}

}
