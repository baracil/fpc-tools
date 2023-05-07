package fpc.tools.springfx;

import fpc.tools.fxmodel.SlotMapper;
import fpc.tools.fxmodel.SlotMapperFactory;
import fpc.tools.fxmodel.ViewSlot;
import org.springframework.boot.ApplicationArguments;

import java.util.Map;

public class SpringSlotMapperFactory implements SlotMapperFactory {

    private final SlotMapperFactory delegate;

    public SpringSlotMapperFactory(ApplicationArguments arguments) {
        if (arguments.containsOption("debug-fx")) {
            delegate = SlotMapperFactory.createForDebug();
        } else {
            delegate = SlotMapperFactory.createDefault();
        }
    }

    @Override
    public SlotMapper create(Map<String, ViewSlot> mapping) {
        return delegate.create(mapping);
    }
}
