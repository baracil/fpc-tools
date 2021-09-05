package net.femtoparsec.tools.fxmodel;

import com.google.common.collect.ImmutableSet;
import fpc.tools.fxmodel.Selection;
import fpc.tools.state.SetState;
import lombok.*;

import java.util.Optional;

@EqualsAndHashCode
@RequiredArgsConstructor
public class FPCSelection<T> implements Selection<T> {

    public static <T> FPCSelection<T> empty() {
        return new FPCSelection<>();
    }

    public static <T> FPCSelection<T> with(@NonNull T mainSelection, @NonNull ImmutableSet<T> selectedElements) {
        assert selectedElements.contains(mainSelection) : "Main selection is not in the selected elements";
        return new FPCSelection<>(mainSelection, new SetState<>(selectedElements));
    }

    /**
     * The main selection. If several elements are selected, this is the one that
     * will be used for single element action. It is also the anchor when performing multi selection
     * operation
     */
    @Getter(AccessLevel.NONE)
    private final T mainSelection;

    /**
     * All the selected elements, including the main selection.
     */
    @NonNull
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
    public @NonNull Selection<T> removeFromSelection(@NonNull T item) {
        if (!selectedElements.contains(item)) {
            return this;
        }
        final SetState<T> newSelectedElements = selectedElements.remove(item);
        if (mainSelection.equals(item)) {
            return newSelectedElements.stream()
                                      .findFirst()
                                      .map(ms -> new FPCSelection<>(ms, newSelectedElements))
                                      .orElseGet(FPCSelection::empty);
        } else {
            return new FPCSelection<>(mainSelection, newSelectedElements);
        }
    }

    @Override
    public @NonNull Selection<T> addToSelection(@NonNull T item) {
        if (item.equals(this.mainSelection)) {
            return this;
        }
        final SetState<T> newSelectedElements = selectedElements.add(item);
        return new FPCSelection<>(item,newSelectedElements);
    }

    @Override
    public @NonNull ImmutableSet<T> getSelectedElements() {
        return selectedElements.getContent();
    }

    @Override
    public boolean isSelected(@NonNull T item) {
        return selectedElements.contains(item);
    }
}

