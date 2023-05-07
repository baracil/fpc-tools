package fpc.tools.action;

import javafx.beans.property.BooleanProperty;

/**
 * Binding between an action and an FX Node.
 */
public interface ActionBinding {

    BooleanProperty filteredProperty();

    void bind();

    void unbind();

    default void setFiltered(boolean filtered) {
        filteredProperty().setValue(filtered);
    }

    default boolean isFiltered() {
        return filteredProperty().get();
    }


}
