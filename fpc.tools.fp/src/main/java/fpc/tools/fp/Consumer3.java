package fpc.tools.fp;

public interface Consumer3<A,B,C> {

    void f(A a,B b,C c);

    default Consumer1<A> f23(B b, C c) {
        return a -> f(a,b,c);
    }


}
