package fpc.tools.fxmodel;

import fpc.tools.fx.FXLoadingResult;
import javafx.scene.Node;
import net.femtoparsec.tools.fxmodel.FPCFXViewInstance;

import java.util.Optional;

public interface FXViewInstance {

    static FXViewInstance with(FXLoadingResult loadingResult) {
        return new FPCFXViewInstance(loadingResult);
    }

    /**
     * @return the node to display
     */
    Optional<Node> node();

    <C> Optional<C> controller(Class<C> controllerType);

    void  showing();

    void  hiding();


    FXViewInstance EMPTY = new FXViewInstance() {
        @Override
        public Optional<Node> node() {
            return Optional.empty();
        }

        @Override
        public <C> Optional<C> controller(Class<C> controllerType) {
            return Optional.empty();
        }

        @Override
        public void showing() {}

        @Override
        public void hiding() {}

    };


}
