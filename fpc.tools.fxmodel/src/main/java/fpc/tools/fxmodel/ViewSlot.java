package fpc.tools.fxmodel;

import fpc.tools.fp.Consumer0;
import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Function2;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ViewSlot {

    /**
     * Name of the slot (used to identify this slot)
     */
    @NonNull
    @Getter
    private final String name;

    @NonNull
    private final Consumer1<? super Node> setNode;
    @NonNull
    private final Consumer0 clearNode;

    @NonNull
    private final Function2<? super FXView, ? super Node, ? extends Node> nodeMapper;

    @NonNull
    private FXViewInstance viewInstance = FXViewInstance.EMPTY;

    private final ObjectProperty<FXView> fxView = new SimpleObjectProperty<>(EmptyFXView.create());

    public ViewSlot(@NonNull String name,
                    @NonNull Consumer1<? super Node> setNode,
                    @NonNull Consumer0 clearNode,
                    @NonNull Function2<? super FXView, ? super Node, ? extends Node> nodeMapper) {
        this.name = name;
        this.clearNode = clearNode;
        this.setNode = setNode;
        this.nodeMapper = nodeMapper;
        LOG.warn("slot '{}' add listener to fxView ", name);
        this.fxView.addListener((l,o,n) -> onViewChange(n));
    }

    private void onViewChange(FXView newView) {
        LOG.warn("Slot '{}' : View Changed to '{}'",name,newView);
        final FXViewInstance oldViewInstance = this.viewInstance;
        clearNode.f();

        this.viewInstance = newView.getViewInstance();
        oldViewInstance.hiding();
        this.viewInstance.showing();
        viewInstance.node()
                    .map(node -> nodeMapper.f(newView,node))
                    .ifPresentOrElse(setNode,clearNode);
    }

    @NonNull
    public FXView getFxView() {
        return fxView.get();
    }

    @NonNull
    public ObjectProperty<FXView> fxViewProperty() {
        return fxView;
    }

    public void setFxView(FXView fxView) {
        LOG.warn("Slot '{}' : set fxView to '{}'",name,fxView.getClass().getSimpleName());
        this.fxView.set(fxView);
    }
}
