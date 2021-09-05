package fpc.tools.fx;

import javafx.scene.Cursor;
import javafx.scene.Node;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.Deque;
import java.util.LinkedList;

@Log4j2
public class FXCursorSetter implements CursorSetter {

    private final Deque<Cursor> stack = new LinkedList<>();

    private Cursor initialCursor = null;

    private Node targetNode = null;

    @Override
    public void setTargetNode(@NonNull Node node) {
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


    private void setCursor(Cursor cursor) {
        if (targetNode != null) {
            targetNode.setCursor(cursor);
        }
    }


    @NonNull
    private static Cursor convert(@NonNull fpc.tools.fx.Cursor cursor) {
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
