package fpc.tools.fx;

import fpc.tools.lang.Subscription;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.NonNull;

import java.util.function.Consumer;

public class SubscriptionHelper {

    public static <E> @NonNull Subscription subscribeOnChange(@NonNull ObservableList<E> toListenerTo, @NonNull ListChangeListener<? super E> listener) {
        toListenerTo.addListener(listener);
        return () -> toListenerTo.removeListener(listener);
    }

    public static <T> @NonNull Subscription subscribeOnChange(@NonNull ObservableValue<T> toListenerTo, @NonNull Runnable listener) {
        return subscribeOnChange(toListenerTo,(l,o,n) -> listener.run());
    }

    public static <T> @NonNull Subscription subscribeOnChange(@NonNull ObservableValue<T> toListenerTo, @NonNull Consumer<T> listener) {
        return subscribeOnChange(toListenerTo,(l,o,n) -> listener.accept(n));
    }

    public static <T> @NonNull Subscription subscribeOnChange(@NonNull ObservableValue<T> toListenerTo, @NonNull ChangeListener<T> listener) {
        toListenerTo.addListener(listener);
        return () -> toListenerTo.removeListener(listener);
    }

    public static <T> @NonNull Subscription bind(@NonNull Property<T> property, @NonNull ObservableValue<? extends T> observable) {
        property.bind(observable);
        return property::unbind;
    }
}
