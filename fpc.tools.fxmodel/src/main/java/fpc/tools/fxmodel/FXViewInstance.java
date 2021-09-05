package fpc.tools.fxmodel;

import fpc.tools.fx.FXLoadingResult;
import javafx.scene.Node;
import lombok.NonNull;
import net.femtoparsec.tools.fxmodel.FPCFXViewInstance;

import java.util.Optional;

public interface FXViewInstance {

    @NonNull
    static FXViewInstance with(FXLoadingResult loadingResult) {
        return new FPCFXViewInstance(loadingResult);
    }

    /**
     * @return the node to display
     */
    @NonNull
    Optional<Node> node();

    void  showing();

    void  hiding();


    @NonNull
    FXViewInstance EMPTY = new FXViewInstance() {
        @Override
        public @NonNull Optional<Node> node() {
            return Optional.empty();
        }

        @Override
        public void showing() {}

        @Override
        public void hiding() {}
    };


}
