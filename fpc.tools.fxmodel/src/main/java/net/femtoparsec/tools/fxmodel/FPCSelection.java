package net.femtoparsec.tools.fxmodel;

import fpc.tools.fxmodel.Selection;
import fpc.tools.state.SetState;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@EqualsAndHashCode
@RequiredArgsConstructor
public class FPCSelection<T> implements Selection<T> {

    public static <T> FPCSelection<T> empty() {
        return new FPCSelection<>();
    }

    public static <T> FPCSelection<T> with(T mainSelection, Set<T> selectedElements) {
        assert selectedElements.contains(mainSelection) : "Main selection is not in the selected elements";
        return new FPCSelection<>(mainSelection, new SetState<>(selectedElements));
    }

    /**
     * The main selection. If several elements are selected, this is the one that
     * will be used for single element action. It is also the anchor when performing multi selection
     * operation
     */
    @Getter(AccessLevel.NONE)
    private final @Nullable T mainSelection;

    /**
     * All the selected elements, including the main selection.
     */
    private final SetState<T> selectedElements;


    private FPCSelection() {
        this.selectedElements = SetState.empty();
        this.mainSelection = null;
    }


    @Override
    public Optional<T> getMainSelection() {
        return Optional.ofNullable(mainSelection);
    }

    @Override
    public Selection<T> removeFromSelection(T item) {
        if (!selectedElements.contains(item)) {
            return this;
        }
        final SetState<T> newSelectedElements = selectedElements.remove(item);
        if (item.equals(mainSelection)) {
            return newSelectedElements.stream()
                                      .findFirst()
                                      .map(ms -> new FPCSelection<>(ms, newSelectedElements))
                                      .orElseGet(FPCSelection::empty);
        } else {
            return new FPCSelection<>(mainSelection, newSelectedElements);
        }
    }

    @Override
    public Selection<T> addToSelection(T item) {
        if (item.equals(this.mainSelection)) {
            return this;
        }
        final SetState<T> newSelectedElements = selectedElements.add(item);
        return new FPCSelection<>(item,newSelectedElements);
    }

    @Override
    public Set<T> getSelectedElements() {
        return selectedElements.getContent();
    }

    @Override
    public boolean isSelected(T item) {
        return selectedElements.contains(item);
    }
}

