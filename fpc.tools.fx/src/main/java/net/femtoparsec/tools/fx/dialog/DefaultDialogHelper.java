package net.femtoparsec.tools.fx.dialog;

import fpc.tools.fp.Function1;
import fpc.tools.fx.*;
import fpc.tools.fx.dialog.*;
import fpc.tools.i18n.Dictionary;
import fpc.tools.lang.SubscriptionHolder;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class DefaultDialogHelper<K extends DialogKindBase<K>> implements DialogHelper<K> {

    private final FXProperties fxProperties;

    private final Dictionary dictionary;

    private final DialogModel<K> dialogModel;

    private final StyleManager styleManager;

    private final DialogPreparer dialogPreparer;


    @Override
    public <I, O, C extends DialogController<I, O>> CompletionStage<Optional<O>> showDialog(
            K dialogKind,
            Class<C> controllerClass,
            Function1<? super Class<C>, ? extends FXLoader> loaderFactory,
            I input) {
        final var result = loaderFactory.apply(controllerClass).load();
        final var controller = result.getController(controllerClass).orElseThrow(() -> new RuntimeException("Invalid controller class " + controllerClass));
        return showDialog(dialogKind, controller, result.getRoot(), input);
    }

    private <I, O> CompletionStage<Optional<O>> showDialog(
            K dialogKind,
            DialogController<I, O> controller,
            Parent root,
            I input
    ) {

        final CompletableFuture<Optional<O>> result = new CompletableFuture<>();
        final SubscriptionHolder subscriptionHolder = new SubscriptionHolder();
        try {

            final DialogInfo<O> dialogInfo = DialogInfoExtractor.extract(dictionary, controller);

            subscriptionHolder.replace(() -> controller.initializeDialog(input));

            final Window owner = dialogKind.getOwnerStage(this.dialogModel.getUnmodifiableDialogStages()).orElse(null);
            final Stage stage = new Stage();

            subscriptionHolder.append(SubscriptionHelper.bind(stage.titleProperty(), controller.titleProperty()));

            stage.initModality(dialogKind.getModality());
            stage.initOwner(owner);
            stage.initStyle(dialogKind.getStageStyle());
            stage.setAlwaysOnTop(dialogKind.isAlwaysOnTop());
            stage.getIcons().setAll(fxProperties.getPrimaryStage().getIcons());
            dialogModel.setDialogData(dialogKind, stage);

            final Scene scene = new Scene(root);
            styleManager.addStylable(scene);
            stage.setScene(scene);
            stage.addEventHandler(WindowEvent.WINDOW_HIDDEN, e -> {
                subscriptionHolder.unsubscribe();
                clearStage(dialogKind);
                result.complete(controller.getDialogState().getValue());
            });
            dialogPreparer.setup(stage, dialogInfo);

            controller.beforeShowing(stage,input);

            stage.show();

            return result;
        } catch (Exception e) {
            subscriptionHolder.unsubscribe();
            clearStage(dialogKind);
            throw e;
        }

    }


    @Override
    public <I> void showDialog(
            K dialogKind,
            FXLoader loader,
            I input
    ) {
        final Stage current = this.dialogModel.getDialogStage(dialogKind).orElse(null);

        if (current != null) {
            if (current.isIconified()) {
                current.setIconified(false);
            }
            current.requestFocus();
            return;
        }

        final FXLoadingResult result = loader.load();
        final DialogController<I, Object> controller = result.getController();
        showDialog(dialogKind, controller, result.getRoot(), input);

    }

    private void clearStage(K dialogKind) {
        dialogModel.clearDialogStage(dialogKind);
    }
}
