package fpc.tools.fp;

public interface Function2<A,B,R> extends Try2<A,B,R,RuntimeException> {

    R apply(A a, B b);

    default Function1<A,R> f2(B b) {
        return a -> apply(a,b);
    }

    default Function1<B,R> f1(A a) {
        return b -> apply(a,b);
    }
}
