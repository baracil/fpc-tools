package fpc.tools.fx;

import javafx.scene.input.KeyEvent;
import lombok.NonNull;

/**
 * @author Bastien Aracil
 */
public interface KeyCatcher {

    void onKeyEvent(@NonNull KeyEvent event, @NonNull ReadOnlyKeyTracker keyTracker);

}
