package fpc.tools.fxmodel;

import lombok.NonNull;

import java.util.Optional;

public interface FXViewProvider {

    <C extends FXView> Optional<FXView> findFXView(Class<? extends C> fxViewType);

    default <C extends FXView> FXView getFXView(Class<? extends C> fxViewType) {
        return findFXView(fxViewType).orElseGet(EmptyFXView::create);
    }

    default FXView getEmptyView() {
        return EmptyFXView.create();
    }

}
