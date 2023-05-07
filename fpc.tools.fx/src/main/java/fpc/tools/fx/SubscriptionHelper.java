package fpc.tools.fx;

import fpc.tools.lang.Subscription;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.function.Consumer;

public class SubscriptionHelper {

    public static <E> Subscription subscribeOnChange(ObservableList<E> toListenerTo, ListChangeListener<? super E> listener) {
        toListenerTo.addListener(listener);
        return () -> toListenerTo.removeListener(listener);
    }

    public static <T> Subscription subscribeOnChange(ObservableValue<T> toListenerTo, Runnable listener) {
        return subscribeOnChange(toListenerTo,(l,o,n) -> listener.run());
    }

    public static <T> Subscription subscribeOnChange(ObservableValue<T> toListenerTo, Consumer<T> listener) {
        return subscribeOnChange(toListenerTo,(l,o,n) -> listener.accept(n));
    }

    public static <T> Subscription subscribeOnChange(ObservableValue<T> toListenerTo, ChangeListener<T> listener) {
        toListenerTo.addListener(listener);
        return () -> toListenerTo.removeListener(listener);
    }

    public static <T> Subscription bind(Property<T> property, ObservableValue<? extends T> observable) {
        property.bind(observable);
        return property::unbind;
    }
}
