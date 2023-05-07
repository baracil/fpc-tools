package fpc.tools.lang;

import fpc.tools.fp.Function1;
import net.femtoparsec.tools.lang.BasicLoader;

import java.util.concurrent.CompletionStage;

public interface Loader<P, R> {

    static <P, R> Loader<P, R> of(Function1<? super P, ? extends R> loadingFunction) {
        return new BasicLoader<>(loadingFunction);
    }

    CompletionStage<R> load(P parameter);

    void cancelLoading();

    Loader<P, R> duplicate();

}
