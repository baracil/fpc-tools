package net.femtoparsec.tools.fxmodel;

import fpc.tools.fx.FXLoadingResult;
import fpc.tools.fxmodel.FXViewInstance;
import fpc.tools.fxmodel.PluggableController;
import javafx.scene.Node;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FPCFXViewInstance implements FXViewInstance {

    private final FXLoadingResult result;

    @Override
    public void showing() {
        result.getController(PluggableController.class).ifPresent(PluggableController::onShowing);
    }

    @Override
    public void hiding() {
        result.getController(PluggableController.class).ifPresent(PluggableController::onHiding);
    }

    @Override
    public Optional<Node> node() {
        return result.getRoot(Node.class);
    }

    @Override
    public <C> Optional<C> controller(Class<C> controllerType) {
        return result.getController(controllerType);
    }
}
