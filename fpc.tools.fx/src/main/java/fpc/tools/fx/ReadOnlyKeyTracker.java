package fpc.tools.fx;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;

/**
 * @author Bastien Aracil
 */
public interface ReadOnlyKeyTracker {

    boolean isPressed(KeyCode keyCode);

    boolean arePressed(KeyCode... keyCodes);

    boolean isMouseOver(Node node);
}
