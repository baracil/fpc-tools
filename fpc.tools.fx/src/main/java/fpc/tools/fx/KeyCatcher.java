package fpc.tools.fx;

import javafx.scene.input.KeyEvent;

/**
 * @author Bastien Aracil
 */
public interface KeyCatcher {

    void onKeyEvent(KeyEvent event, ReadOnlyKeyTracker keyTracker);

}
