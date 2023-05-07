package fpc.tools.fx;

import fpc.tools.lang.Subscription;
import javafx.scene.Node;
import javafx.stage.Stage;
import lombok.NonNull;

/**
 * @author Bastien Aracil
 */
public interface KeyTracker extends ReadOnlyKeyTracker {

    static KeyTracker create() {
        return KeyTrackerFactory.getInstance().create();
    }

    void attach(Stage target);

    void detach();

    Subscription addKeyCatcher(Node node, KeyCatcher keyCatcher);

    Subscription addKeyCatcherOnlyIfOver(Node node, KeyCatcher keyCatcher);

}
