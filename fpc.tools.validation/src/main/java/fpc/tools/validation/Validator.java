package fpc.tools.validation;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Predicate1;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.Optional;

@SuppressWarnings("unused")
public interface Validator<O,V extends Validator<O,V>> {

    @NonNull
    V isNotNull();

    V addError(@NonNull String errorType);

    @NonNull
    V errorIf(@NonNull Predicate1<O> test, @NonNull String errorType);

    @NonNull
    V errorIfNot(@NonNull Predicate1<O> test, @NonNull String errorType);

    /**
     * @return an optional containing the validated value only is this validator is valid.
     */
    @NonNull
    Optional<O> getValidValue();

    /**
     * @return the the validated value if it is valid and not null
     * @throws IllegalStateException if the value is null or invalid
     */
    @NonNull
    O getValue();

    @NonNull <T, V2 extends Validator<T,V2>> V2 map(
            @NonNull Function1<? super O, ? extends T> mapper,
            @NonNull ValidatorFactory<? super T, ? extends V2> factory);

    @NonNull <T> Validator<T,?> map(
            @NonNull Function1<? super O, ? extends T> mapper);

    @NonNull
    PathValidator toPathValidator(Function1<? super O, ? extends Path> pathBuilder);

    boolean isValid();

    interface Mapper<O, T> {
        T map(@NonNull O value);

        @NonNull
        default OptionalMapper<O, T> toOptional() {
            return o -> Optional.ofNullable(map(o));
        }
    }

    interface OptionalMapper<O, T> {
        @NonNull
        Optional<T> map(@NonNull O value);
    }
}
