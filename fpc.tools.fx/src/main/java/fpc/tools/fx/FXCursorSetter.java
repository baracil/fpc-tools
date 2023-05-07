package fpc.tools.fx;

import jakarta.annotation.Nullable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.Deque;
import java.util.LinkedList;

@Slf4j
public class FXCursorSetter implements CursorSetter {

    private final Deque<Cursor> stack = new LinkedList<>();

    private @Nullable Cursor initialCursor = null;

    private @Nullable Node targetNode = null;

    @Override
    public void setTargetNode(Node node) {
        if (targetNode != null) {
            targetNode.setCursor(initialCursor);
        }
        targetNode = node;
        initialCursor = node.getCursor();
    }

    @Override
    public void pushCursor(fpc.tools.fx.Cursor cursor) {
        Cursor nativeCursor = convert(cursor);
        this.stack.addLast(nativeCursor);
        this.setCursor(nativeCursor);
    }

    @Override
    public void popCursor() {
        stack.removeLast();
        final Cursor cursor = stack.isEmpty()?initialCursor:stack.getLast();
        this.setCursor(cursor);
    }

    @Override
    public void popAndPushCursor(fpc.tools.fx.Cursor cursor) {
        this.stack.removeLast();
        this.pushCursor(cursor);
    }


    private void setCursor(@Nullable Cursor cursor) {
        if (targetNode != null) {
            targetNode.setCursor(cursor);
        }
    }


    private static Cursor convert(fpc.tools.fx.Cursor cursor) {
        return switch (cursor) {
            case CROSSHAIR -> Cursor.CROSSHAIR;
            case TEXT -> Cursor.TEXT;
            case WAIT -> Cursor.WAIT;
            case SW_RESIZE -> Cursor.SW_RESIZE;
            case SE_RESIZE -> Cursor.SE_RESIZE;
            case NW_RESIZE -> Cursor.NW_RESIZE;
            case NE_RESIZE -> Cursor.NE_RESIZE;
            case N_RESIZE -> Cursor.N_RESIZE;
            case S_RESIZE -> Cursor.S_RESIZE;
            case W_RESIZE -> Cursor.W_RESIZE;
            case E_RESIZE -> Cursor.E_RESIZE;
            case OPEN_HAND -> Cursor.OPEN_HAND;
            case CLOSED_HAND -> Cursor.CLOSED_HAND;
            case HAND -> Cursor.HAND;
            case MOVE -> Cursor.MOVE;
            default -> Cursor.DEFAULT;
        };

    }
}
