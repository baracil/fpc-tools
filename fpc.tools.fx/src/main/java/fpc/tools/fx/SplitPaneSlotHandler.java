package fpc.tools.fx;

import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;

public class SplitPaneSlotHandler {

    private final SplitPane splitPane;

    public SplitPaneSlotHandler(SplitPane splitPane) {
        this.splitPane = splitPane;
        while (splitPane.getItems().size() < 2) {
            splitPane.getItems().add(new AnchorPane());
        }
    }

    public void setLeft(Node node) {
        this.splitPane.getItems().set(0,anchorIfNull(node));
    }

    public void setRight(Node node) {
        this.splitPane.getItems().set(1,anchorIfNull(node));
    }

    private Node anchorIfNull(Node node) {
        return node == null ? new AnchorPane():node;
    }

}
