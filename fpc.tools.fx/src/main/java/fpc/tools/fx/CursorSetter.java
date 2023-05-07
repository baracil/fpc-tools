package fpc.tools.fx;


import javafx.scene.Node;

/**
 * @author Bastien Aracil
 */
public interface CursorSetter {

    void setTargetNode(Node node);

    void pushCursor(Cursor cursor);

    void popCursor();

    void popAndPushCursor(Cursor cursor);
}
