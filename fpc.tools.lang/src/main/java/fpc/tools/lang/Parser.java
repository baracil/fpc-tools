package fpc.tools.lang;

import fpc.tools.fp.Function1;
import fpc.tools.fp.TryResult;
import lombok.NonNull;

public interface Parser<T> {

    @NonNull TryResult<ParsingFailure,T> parse(@NonNull String value);

    @NonNull Class<T> targetType();


    Parser<Integer> PARSE_INT = with(Integer::parseInt,Integer.class);
    Parser<Long> PARSE_LONG = with(Long::parseLong,Long.class);

    Parser<Float> PARSE_FLOAT = with(Float::parseFloat,Float.class);
    Parser<Double> PARSE_DOUBLE = with(Double::parseDouble,Double.class);


    static @NonNull <T> Parser<T> with(@NonNull Function1<? super String, ? extends T> unsafeParser, @NonNull Class<T> type) {
        return new Parser<T>() {
            @Override
            public @NonNull TryResult<ParsingFailure,T> parse(@NonNull String value) {
                try {
                    final var t = unsafeParser.apply(value);
                    return TryResult.success(t);
                } catch (Throwable t) {
                    ThrowableTool.interruptIfCausedByInterruption(t);
                    return TryResult.failure(new ParsingFailure(value,type,t));
                }
            }

            @Override
            public @NonNull Class<T> targetType() {
                return type;
            }
        };
    }


}
