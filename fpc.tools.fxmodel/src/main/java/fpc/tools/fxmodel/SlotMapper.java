package fpc.tools.fxmodel;

import fpc.tools.fp.Consumer0;
import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Function2;
import javafx.scene.Node;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class SlotMapper implements SlotRegistry {

    private final Map<String, ViewSlot> mapping;

    private final Function2<FXView, Node,Node> nodeMapper;

    @Override
    public void register(String name,
                                      Consumer1<? super Node> nodeSetter,
                                      Consumer0 nodeClearer) {
        final ViewSlot viewSlot = new ViewSlot(name,nodeSetter,nodeClearer,nodeMapper);
        this.mapping.put(name,viewSlot);
    }

}
