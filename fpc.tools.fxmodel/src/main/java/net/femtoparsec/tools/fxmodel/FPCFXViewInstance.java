package net.femtoparsec.tools.fxmodel;

import fpc.tools.fx.FXLoadingResult;
import fpc.tools.fxmodel.FXViewInstance;
import fpc.tools.fxmodel.PluggableController;
import javafx.scene.Node;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FPCFXViewInstance implements FXViewInstance {

    @NonNull
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
    public @NonNull Optional<Node> node() {
        return result.getRoot(Node.class);
    }

    @Override
    public @NonNull <C> Optional<C> controller(@NonNull Class<C> controllerType) {
        return result.getController(controllerType);
    }
}
