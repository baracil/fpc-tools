package fpc.tools.fp;

import lombok.NonNull;

public interface Consumer3<A,B,C> {

    void f(@NonNull A a,@NonNull B b,@NonNull C c);

    default @NonNull Consumer1<A> f23(@NonNull B b, @NonNull C c) {
        return a -> f(a,b,c);
    }


}
