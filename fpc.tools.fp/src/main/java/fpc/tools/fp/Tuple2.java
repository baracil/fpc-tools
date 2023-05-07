package fpc.tools.fp;

public record Tuple2<A, B>(A v1, B v2) {

    public void consume(Consumer2<? super A, ? super B> consumer) {
        consumer.accept(v1,v2);
    }

}
