package net.femtoparsec.tools.fxmodel;

import fpc.tools.fp.Function2;
import fpc.tools.fx.NodeWrapper;
import fpc.tools.fxmodel.FXView;
import javafx.scene.Node;
import lombok.NonNull;


public class DebugFXView implements Function2<FXView, Node, Node> {

    private final NodeWrapper nodeMapper = new NodeWrapper();

    @NonNull
    @Override
    public Node apply(@NonNull FXView fxView, @NonNull Node node) {
        return nodeMapper.apply(fxView.getClass().getSimpleName(), node);
    }

}
