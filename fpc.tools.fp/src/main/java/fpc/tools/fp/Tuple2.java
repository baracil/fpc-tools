package fpc.tools.fp;

import lombok.NonNull;

public record Tuple2<A, B>(@NonNull A v1, @NonNull B v2) {

    public void consume(@NonNull Consumer2<? super A, ? super B> consumer) {
        consumer.accept(v1,v2);
    }

}
