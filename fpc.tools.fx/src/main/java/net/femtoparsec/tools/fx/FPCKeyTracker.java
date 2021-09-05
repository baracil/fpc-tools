package net.femtoparsec.tools.fx;

import fpc.tools.fx.KeyCatcher;
import fpc.tools.fx.KeyCatcherOnlyIfOver;
import fpc.tools.fx.KeyTracker;
import fpc.tools.lang.Subscription;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.NonNull;

import java.util.*;

/**
 * @author Bastien Aracil
 */
public class FPCKeyTracker implements KeyTracker {

    private Stage target = null;

    private final EventHandler<KeyEvent> keyEventEventHandler = this::handleKeyEvent;
    private final EventHandler<MouseEvent> mouseEventHandler = this::handleMouseEvent;


    private final Set<KeyCode> pressedKeys = new HashSet<>();

    private final Map<Node, List<KeyCatcher>> keyCatchers = new HashMap<>();

    private MouseEvent lastMouseEvent = null;

    @Override
    public void attach(@NonNull Stage target) {
        this.detach();
        this.target = target;
        this.target.addEventFilter(KeyEvent.ANY, keyEventEventHandler);
        this.target.addEventFilter(MouseEvent.ANY, mouseEventHandler);
    }

    @Override
    public void detach() {
        if (target != null) {
            target.removeEventFilter(KeyEvent.ANY, keyEventEventHandler);
        }
        target = null;
    }

    private void handleKeyEvent(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            pressedKeys.add(event.getCode());
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {
            pressedKeys.remove(event.getCode());
        }

        for (Map.Entry<Node, List<KeyCatcher>> entry : keyCatchers.entrySet()) {
            if (event.isConsumed()) {
                break;
            }
            if (entry.getKey().isHover()) {
                for (KeyCatcher keyCatcher : entry.getValue()) {
                    if (event.isConsumed()) {
                        break;
                    } else {
                        keyCatcher.onKeyEvent(event, this);
                    }
                }
            }
        }
    }

    @Override
    public boolean isPressed(KeyCode keyCode) {
        return pressedKeys.contains(keyCode);
    }

    @Override
    public boolean arePressed(@NonNull KeyCode... keyCodes) {
        return pressedKeys.containsAll(Arrays.asList(keyCodes));
    }

    @Override
    public Subscription addKeyCatcher(@NonNull Node node, @NonNull KeyCatcher keyCatcher) {
        //IMPROVE implementation for hierarchical node structure
        final List<KeyCatcher> keyCatchers = this.keyCatchers.computeIfAbsent(node, n -> new ArrayList<>());
        keyCatchers.add(keyCatcher);
        return () -> remove(node, keyCatcher);
    }

    @Override
    public @NonNull Subscription addKeyCatcherOnlyIfOver(Node node, KeyCatcher keyCatcher) {
        return addKeyCatcher(node, new KeyCatcherOnlyIfOver(node, keyCatcher));
    }

    private void handleMouseEvent(MouseEvent mouseEvent) {
        this.lastMouseEvent = mouseEvent;
    }

    private void remove(@NonNull Node node, @NonNull KeyCatcher keyCatcher) {
        final List<KeyCatcher> keyCatchers = this.keyCatchers.get(node);
        if (keyCatchers != null) {
            keyCatchers.remove(keyCatcher);
        }
        if (keyCatchers != null && keyCatchers.isEmpty()) {
            this.keyCatchers.remove(node);
        }
    }

    @Override
    public boolean isMouseOver(@NonNull Node node) {
        final MouseEvent last = lastMouseEvent;
        if (last == null) {
            return false;
        }
        return node.localToScreen(node.getBoundsInLocal()).contains(last.getScreenX(), last.getScreenY());
    }


}
