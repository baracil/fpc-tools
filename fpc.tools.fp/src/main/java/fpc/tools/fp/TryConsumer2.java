package fpc.tools.fp;

public interface TryConsumer2<A,B,E extends Throwable> {

    static <A,B,E extends Throwable> TryConsumer2<A,B,E> of(TryConsumer2<A,B,E> tryConsumer1) {
        return tryConsumer1;
    }

    void accept(A a, B b) throws E;
    
}
