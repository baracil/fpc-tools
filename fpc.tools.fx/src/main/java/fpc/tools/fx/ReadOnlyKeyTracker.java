package fpc.tools.fx;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import lombok.NonNull;

/**
 * @author Bastien Aracil
 */
public interface ReadOnlyKeyTracker {

    boolean isPressed(@NonNull KeyCode keyCode);

    boolean arePressed(@NonNull KeyCode... keyCodes);

    boolean isMouseOver(@NonNull Node node);
}
