package fpc.tools.fx;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.NonNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;


public class NodeWrapper implements BiFunction<String, Node, Node> {

    public static final Paint[] PAINTS = {
            Color.rgb(175, 33, 33),
            Color.rgb(33, 175, 33),
            Color.rgb(33, 33, 255),
    };

    private static final AtomicInteger counter = new AtomicInteger(0);

    @NonNull
    @Override
    public Node apply(@NonNull String labelText, @NonNull Node node) {
        FXTools.fitToAnchorPane(node);
        final Label label = new Label(labelText);
        label.getStyleClass().add("debug-node-label");

        FXTools.setAnchorConstraints(label,0.0,null,null,0.0);

        final Paint paint = getPaint();

        final AnchorPane holder = new AnchorPane(node,label);
        holder.setBorder(new Border(new BorderStroke(paint, BorderStrokeStyle.DASHED, CornerRadii.EMPTY,BorderStroke.THIN)));
        return holder;
    }

    private static Paint getPaint() {
        int v = counter.getAndIncrement();
        return PAINTS[v%PAINTS.length];
    }
}
