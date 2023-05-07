package fpc.tools.fp;

public interface TryConsumer1<A,E extends Throwable> {

    static <A,E extends Throwable> TryConsumer1<A,E> of(TryConsumer1<A,E> tryConsumer1) {
        return tryConsumer1;
    }

    void accept(A a) throws E;
    
}
