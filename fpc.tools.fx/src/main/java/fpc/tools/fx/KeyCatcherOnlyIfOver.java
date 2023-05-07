package fpc.tools.fx;

import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyCatcherOnlyIfOver implements KeyCatcher {

    private final Node node;

    private final KeyCatcher keyCatcher;


    @Override
    public void onKeyEvent(KeyEvent event, ReadOnlyKeyTracker keyTracker) {
        if (keyTracker.isMouseOver(node)) {
            keyCatcher.onKeyEvent(event,keyTracker);
        }
    }
}
