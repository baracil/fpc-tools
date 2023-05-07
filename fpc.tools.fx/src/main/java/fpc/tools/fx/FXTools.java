package fpc.tools.fx;

import fpc.tools.fp.Consumer2;
import fpc.tools.fp.Function0;
import fpc.tools.fp.Function1;
import fpc.tools.lang.Loader;
import fpc.tools.lang.Subscription;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Labeled;
import javafx.scene.layout.AnchorPane;
import net.femtoparsec.tools.fx.CompleteInFXLoader;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FXTools {

    private static final PseudoClass NOT_EDITING = PseudoClass.getPseudoClass("not-editing");

    public static <T> Subscription subscribe(ObservableValue<T> observable, ChangeListener<T> listener) {
        observable.addListener(listener);
        return () -> observable.removeListener(listener);
    }

    public static <T> Subscription subscribe(Observable observable, InvalidationListener listener) {
        observable.addListener(listener);
        return () -> observable.removeListener(listener);
    }

    public static <P,R> Loader<P,R> completeInFX(Loader<P,R> loader) {
        if (loader instanceof CompleteInFXLoader) {
            return loader;
        }
        return new CompleteInFXLoader<>(loader);
    }

    public static <C> CompletionStage<C> completeLater(CompletionStage<C> stage) {
        final CompletableFuture<C> future = new CompletableFuture<>();
        stage.whenComplete((c,t) -> {
            Platform.runLater(() -> {
                if (t != null) {
                    future.completeExceptionally(t);
                } else {
                    future.complete(c);
                }
            });
        });
        return future;
    }

    public static Consumer<? super Node> anchorPaneFitter(AnchorPane parent) {
        return n -> {
            if (n == null) {
                parent.getChildren().clear();
            } else {
                fitToAnchorPane(n);
                parent.getChildren().setAll(n);
            }
        };
    }

    public static <T> BooleanBinding anyOf(Collection<T> items, Function1<? super T, ? extends ObservableBooleanValue> observableGetter) {
        final ObservableBooleanValue[] observables = items.stream().map(observableGetter).toArray(ObservableBooleanValue[]::new);
        return Bindings.createBooleanBinding(() -> Arrays.stream(observables).anyMatch(ObservableBooleanValue::get),observables);
    }

    public static void fitToAnchorPane(Node node) {
        setAnchorConstraints(node, 0.0, 0.0, 0.0, 0.0);
    }

    public static Node setAnchorConstraints(Node node, @Nullable Double top, @Nullable Double right, @Nullable Double bottom, @Nullable Double left) {
        AnchorPane.setTopAnchor(node, top);
        AnchorPane.setRightAnchor(node, right);
        AnchorPane.setBottomAnchor(node, bottom);
        AnchorPane.setLeftAnchor(node, left);
        return node;
    }

    public static <L extends Labeled> L setAlignment(L c, Pos alignment) {
        c.setAlignment(alignment);
        return c;
    }

    public static <L extends Labeled> L centeredAligned(L c) {
        return setAlignment(c, Pos.CENTER);
    }


    public static <C extends ComboBoxBase<?>> C addNotEditingPseudoClass(C comboBoxBase) {
        comboBoxBase.showingProperty().addListener(l -> updateNotEditingPseudoState(comboBoxBase));
        comboBoxBase.armedProperty().addListener(l -> updateNotEditingPseudoState(comboBoxBase));
        updateNotEditingPseudoState(comboBoxBase);

        return comboBoxBase;
    }

    public static void updateNotEditingPseudoState(ComboBoxBase<?> comboBoxBase) {
        final boolean notEditing = !comboBoxBase.isArmed() && !comboBoxBase.isShowing();
        comboBoxBase.pseudoClassStateChanged(NOT_EDITING, notEditing);
    }

    public static <T> void synchronize(Collection<T> values,ObservableList<T> items) {
        if (values.isEmpty()) {
            items.clear();
        }
        if (items.size() > values.size()) {
            items.remove(values.size(),items.size());
        }

        final Iterator<T> valueItr = values.iterator();
        final ListIterator<T> itemItr = items.listIterator();

        while (valueItr.hasNext()) {
            final T value = valueItr.next();
            if (itemItr.hasNext()) {
                final T item = itemItr.next();
                if (!value.equals(item)) {
                    itemItr.set(value);
                }
            } else {
                itemItr.add(value);
            }
        }
    }


    public static <V, T> void synchronize(List<V> newValues,
                                          ObservableList<T> items,
                                          Function0<? extends T> itemFactory,
                                          Consumer2<? super V, ? super T> itemInitializer) {
        adaptSize(items, newValues.size(), itemFactory);
        assert newValues.size() == items.size();

        final Iterator<V> valueItr = newValues.iterator();
        final Iterator<T> elementItr = items.iterator();
        while (valueItr.hasNext() && elementItr.hasNext()) {
            final V value = valueItr.next();
            final T element = elementItr.next();

            itemInitializer.f(value, element);
        }

    }

    private static <T> void adaptSize(ObservableList<T> observableList, int requestedSize, Function0<? extends T> factory) {
        if (requestedSize <= 0) {
            observableList.clear();
        }

        final int difference = requestedSize - observableList.size();
        if (difference == 0) {
            return;
        }
        if (difference < 0) {
            observableList.remove(requestedSize, observableList.size());
        }
        if (difference > 0) {
            final List<T> newElements = IntStream.range(0, difference)
                                                 .mapToObj(i -> factory.apply())
                                                 .collect(Collectors.toList());
            observableList.addAll(newElements);
        }
    }

    public static void bindManagedToVisible(Node... nodes) {
        Stream.of(nodes).forEach(n -> n.managedProperty().bind(n.visibleProperty()));
    }

    public static void bindManagedToVisible(Collection<? extends Node> nodes) {
        nodes.forEach(n -> n.managedProperty().bind(n.visibleProperty()));
    }

    public static void checkIsFXThread() {
        if (Platform.isFxApplicationThread()) {
            return;
        }
        System.err.println("Not called from FX Thread");
        Thread.dumpStack();
    }

    public static boolean isFXThread() {
        return Platform.isFxApplicationThread();
    }

    public static void runInFXThread(Runnable runnable) {
        if (isFXThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }
}
