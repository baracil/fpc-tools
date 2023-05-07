package fpc.tools.lang;

import fpc.tools.fp.Consumer1;
import fpc.tools.fp.Consumer2;
import fpc.tools.fp.Consumer3;
import lombok.NonNull;

import java.util.List;

public interface Listeners<L> {

    static <L> Listeners<L> create() {
        return ListenersFactory.getInstance().create();
    }

    static <L> Listeners<L> create(List<L> initialListeners) {
        return ListenersFactory.getInstance().create(initialListeners);
    }

    boolean isEmpty();

    Subscription addListener(L listener);

    void forEachListeners(Consumer1<? super L> action);

    default <A> void forEachListeners(Consumer2<? super L, ? super A> action, A a) {
        forEachListeners(action.f2(a));
    }

    default <A,B> void forEachListeners(Consumer3<? super L, ? super A, ? super B> action, A a, B b) {
        forEachListeners(action.f23(a,b));
    }


}
