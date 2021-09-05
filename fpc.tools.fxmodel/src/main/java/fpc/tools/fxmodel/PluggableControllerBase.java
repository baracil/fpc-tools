package fpc.tools.fxmodel;

import lombok.NonNull;

import java.util.stream.Stream;

public abstract class PluggableControllerBase implements PluggableController {

    @Override
    public final void onShowing() {
        this.doOnShowing();
        this.subPluggableController().forEach(PluggableController::onShowing);
    }

    @Override
    public final void onHiding() {
        this.subPluggableController().forEach(PluggableController::onHiding);
        this.doOnHiding();
    }

    public abstract void doOnShowing();
    public abstract void doOnHiding();

    /**
     * @return any child pluggable controllers this controller used. They are used to automatically
     * call {@link #onShowing()} and {@link #onHiding()}
     */
    protected abstract @NonNull Stream<PluggableController> subPluggableController();
}
