package fpc.tools.fxmodel;

import net.femtoparsec.tools.fxmodel.DebugFXView;

import java.util.Map;

public interface SlotMapperFactory {

    SlotMapper create(Map<String,ViewSlot> mapping);

    static SlotMapperFactory createDefault() {
        return m -> new SlotMapper(m, (fxView, node) -> node);
    }

    static SlotMapperFactory createForDebug() {
        return m -> new SlotMapper(m, new DebugFXView());
    }


}
