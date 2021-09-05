package fpc.tools.lang;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Consumer2;
import fpc.tools.fp.Consumer3;
import lombok.NonNull;

public interface Listeners<L> {

    @NonNull
    static <L> Listeners<L> create() {
        return ListenersFactory.getInstance().create();
    }

    @NonNull
    Subscription addListener(@NonNull L listener);

    void forEachListeners(@NonNull Consumer1<? super L> action);

    default <A> void forEachListeners(@NonNull Consumer2<? super L, ? super A> action, @NonNull A a) {
        forEachListeners(action.f2(a));
    }

    default <A,B> void forEachListeners(@NonNull Consumer3<? super L, ? super A, ? super B> action, @NonNull A a, @NonNull B b) {
        forEachListeners(action.f23(a,b));
    }


}
