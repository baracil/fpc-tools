package fpc.tools.lang;

import fpc.tools.fp.Function1;
import lombok.NonNull;
import net.femtoparsec.tools.lang.BasicLoader;

import java.util.concurrent.CompletionStage;

public interface Loader<P, R> {

    @NonNull
    static <P, R> Loader<P, R> of(@NonNull Function1<? super P, ? extends R> loadingFunction) {
        return new BasicLoader<>(loadingFunction);
    }

    @NonNull
    CompletionStage<R> load(@NonNull P parameter);

    void cancelLoading();

    @NonNull
    Loader<P, R> duplicate();

}
