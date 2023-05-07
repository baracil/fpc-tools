package fpc.tools.fx;

import fpc.tools.fp.Function1;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicCellValueFactory<S,T> implements Callback<TableColumn.CellDataFeatures<S,T>, ObservableValue<T>> {

    public static <S,T> BasicCellValueFactory<S,T> create(Function1<? super S, ? extends T> getter) {
        return new BasicCellValueFactory<>(getter);
    }

    private final Function1<? super S, ? extends T> getter;

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<S, T> cellDataFeatures) {
        final S value = cellDataFeatures.getValue();
        final T cellValue = value == null ? null : getter.apply(value);
        return new ReadOnlyObjectWrapper<>(cellValue);
    }
}
