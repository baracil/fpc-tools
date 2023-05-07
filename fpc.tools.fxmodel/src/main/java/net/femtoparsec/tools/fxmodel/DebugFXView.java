package net.femtoparsec.tools.fxmodel;

import fpc.tools.fp.Function2;
import fpc.tools.fx.NodeWrapper;
import fpc.tools.fxmodel.FXView;
import javafx.scene.Node;


public class DebugFXView implements Function2<FXView, Node, Node> {

    private final NodeWrapper nodeMapper = new NodeWrapper();

    @Override
    public Node apply(FXView fxView, Node node) {
        return nodeMapper.apply(fxView.getClass().getSimpleName(), node);
    }

}
