package fpc.tools.fxmodel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public abstract class DynamicController {

    private Map<String, ViewSlot> slots = new HashMap<>();

    private final SlotMapperFactory slotMapperFactory;

    private final FXViewProvider fxViewProvider;

    public void initialize() {
        this.initializeSlots(slotMapperFactory.create(slots));
        this.performControllerInitialization();
    }

    protected abstract void initializeSlots(SlotRegistry slotRegistry);
    protected abstract void performControllerInitialization();

    protected boolean setSlotViewEmpty(String slotName) {
        return setSlotView(slotName,EmptyFXView.class);
    }

    protected boolean setSlotView(String slotName, Class<? extends FXView> fxViewType) {
        final ViewSlot slot = slots.get(slotName);
        if (slot == null) {
            LOG.warn("Could not find slot with name {} in controller {}", slotName, getClass());
            return false;
        }

        final FXView fxView = fxViewProvider.getFXView(fxViewType);
        slot.setFxView(fxView);
        return true;
    }

}
