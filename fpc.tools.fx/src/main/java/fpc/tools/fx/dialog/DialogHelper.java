package fpc.tools.fx.dialog;

import fpc.tools.fp.Function1;
import fpc.tools.fx.FXLoader;
import fpc.tools.fx.FXProperties;
import fpc.tools.fx.StyleManager;
import fpc.tools.i18n.Dictionary;
import lombok.NonNull;
import net.femtoparsec.tools.fx.dialog.DefaultDialogHelper;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

public interface DialogHelper<K extends DialogKindBase<K>> {


    static <K extends DialogKindBase<K>> DialogHelper<K> create(
            @NonNull FXProperties fxProperties,
            @NonNull Dictionary dictionary,
            @NonNull DialogModel<K> dialogModel,
            @NonNull StyleManager styleManager,
            @NonNull DialogPreparer dialogPreparer
            ) {
        return new DefaultDialogHelper<K>(
                fxProperties,
                dictionary,
                dialogModel,
                styleManager,
                dialogPreparer
        );
    }

    <I> void showDialog(
            @NonNull K dialogKind,
            @NonNull FXLoader loader,
            @NonNull I input);

    @NonNull
    <I,O,C extends DialogController<I,O>> CompletionStage<Optional<O>> showDialog(
            @NonNull K dialogKind,
            @NonNull Class<C> controllerClass,
            @NonNull Function1<? super Class<C>, ? extends FXLoader> loaderFactory,
            @NonNull I input);

}
