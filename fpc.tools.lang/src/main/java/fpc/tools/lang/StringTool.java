package fpc.tools.lang;

import fpc.tools.fp.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author bastien.a
 */
public class StringTool {

    public static IsEndingWithSuffix IS_ENDING_WITH = new IsEndingWithSuffix();

    public static class IsEndingWithSuffix implements Predicate2<String,String> {
        @Override
        public boolean test(@NonNull String str, @NonNull String suffix) {
            return str.endsWith(suffix);
        }

        public Predicate1<String> withString(String str) {
            return f1(str);
        }
        public Predicate1<String> withSuffix(String suffix) {
            return f2(suffix);
        }
    }

    private static final Pattern NEW_LINE_PATTERN = Pattern.compile("\\r?\\n");

    public static Function1<String,Stream<String>> LINES = s -> Arrays.stream(NEW_LINE_PATTERN.split(s));

    public static Function1<Stream<String>, String> UNLINES = s -> s.collect(Collectors.joining("\n"));

    public static Function1<String,Optional<String>> BLANK_TO_OPTIONAL = s -> Optional.of(s).filter(o -> !o.trim().isEmpty());


    public static Object toStringLazy(Function0<Object> supplier) {
        return new LazyToString(supplier);
    }

    public static <A> Object toStringLazy(Function1<A, Object> transformer, A a) {
        return new LazyToString(() -> transformer.apply(a));
    }

    public static <A,B> Object toStringLazy(Function2<A, B,Object> transformer, A a, B b) {
        return new LazyToString(() -> transformer.apply(a,b));
    }

    public static <A,B,C> Object toStringLazy(Function3<A, B, C,Object> transformer, A a, B b, C c) {
        return new LazyToString(() -> transformer.apply(a,b,c));
    }

    @RequiredArgsConstructor
    private static class LazyToString {

        @NonNull
        private final Function0<Object> supplier;

        @Override
        public String toString() {
            return supplier.fSafe().getEither().merge(ThrowableTool.ONE_LINE_MESSAGE_EXTRACTOR, String::valueOf);
        }
    }

}
