package fpc.tools.fp;

public interface Function3<A,B,C,R> {

    R apply(A a, B b, C c);

    default Function1<A,R> f23(B b, C c) {
        return a -> apply(a,b,c);
    }
}
