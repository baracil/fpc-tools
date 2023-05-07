package fpc.tools.validation;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Predicate1;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Optional;

@SuppressWarnings("unused")
public interface Validator<O,V extends Validator<O,V>> {

    V isNotNull();

    V addError(String errorType);

    V errorIf(Predicate1<O> test, String errorType);

    V errorIfNot(Predicate1<O> test, String errorType);

    /**
     * @return an optional containing the validated value only is this validator is valid.
     */
    Optional<O> getValidValue();

    /**
     * @return the validated value if it is valid and not null
     * @throws IllegalStateException if the value is null or invalid
     */
    O getValue();

    <T, V2 extends Validator<T,V2>> V2 map(
            Function1<? super O, ? extends T> mapper,
            ValidatorFactory<? super T, ? extends V2> factory);

    <T> Validator<T,?> map(
            Function1<? super O, ? extends T> mapper);

    PathValidator toPathValidator(Function1<? super O, ? extends Path> pathBuilder);

    boolean isValid();

    interface Mapper<O, T> {
        @Nullable T map(O value);

        default OptionalMapper<O, T> toOptional() {
            return o -> Optional.ofNullable(map(o));
        }
    }

    interface OptionalMapper<O, T> {
        Optional<T> map(O value);
    }
}
