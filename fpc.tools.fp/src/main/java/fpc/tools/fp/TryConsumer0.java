package fpc.tools.fp;

public interface TryConsumer0<E extends Throwable> {

    static <E extends Throwable> TryConsumer0<E> of(TryConsumer0<E> tryConsumer0) {
        return tryConsumer0;
    }

    void accept() throws E;

    default Try0<Nil,E> toTry() {
        return () -> {accept();return Nil.NULL;};
    }

}
