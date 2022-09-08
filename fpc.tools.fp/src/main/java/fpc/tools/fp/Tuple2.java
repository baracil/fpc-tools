package fpc.tools.fp;

import lombok.NonNull;

public record Tuple2<A, B>(@NonNull A v1, @NonNull B v2) {

}
