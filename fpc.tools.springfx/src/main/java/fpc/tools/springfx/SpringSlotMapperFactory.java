package fpc.tools.springfx;

import fpc.tools.fxmodel.SlotMapper;
import fpc.tools.fxmodel.SlotMapperFactory;
import fpc.tools.fxmodel.ViewSlot;
import lombok.NonNull;
import org.springframework.boot.ApplicationArguments;

import java.util.Map;

public class SpringSlotMapperFactory implements SlotMapperFactory {

    @NonNull
    private final SlotMapperFactory delegate;

    public SpringSlotMapperFactory(@NonNull ApplicationArguments arguments) {
        if (arguments.containsOption("debug-fx")) {
            delegate = SlotMapperFactory.createForDebug();
        } else {
            delegate = SlotMapperFactory.createDefault();
        }
    }

    @Override
    public @NonNull SlotMapper create(@NonNull Map<String, ViewSlot> mapping) {
        return delegate.create(mapping);
    }
}
