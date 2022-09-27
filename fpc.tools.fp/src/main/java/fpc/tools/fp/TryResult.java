package fpc.tools.fp;

import lombok.NonNull;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Result of a safe evaluation of a try.
 *
 * @author Bastien Aracil
 */
public final class TryResult<A, E extends Throwable> {

    public static <A,E extends Throwable> TryResult<A,E> failure(E e) {
        final Either<E,A> either = Either.left(e);
        return new TryResult<>(either);
    }

    public static <A,E extends Throwable> TryResult<A,E> success(@NonNull A result) {
        return new TryResult<>(Either.right(result));
    }

    @Override
    public String toString() {
        return getEither().merge(e -> "Failure("+e+")", a -> "Success("+a+")");
    }

    /**
     * The underlying either containing the result or the exception
     */
    @NonNull
    private final Either<E,A> result;

    public TryResult(@NonNull Either<E, A> result) {
        this.result = result;
    }

    /**
     * @return true if this result is a failure. use {@link #getException()} to get the exception (in this case the optional is not empty)
     */
    public boolean isFailure() {
        return result.isLeft();
    }

    /**
     * @return true if this result is a success. use {@link #getResult()} to get the result (in this case the optional is not empty)
     */
    public boolean isSuccess() {
        return !isFailure();
    }

    /**
     * @return an optional containing the exception of this try result if this is a failure, an empty optional otherwise
     */
    @NonNull
    public Optional<E> getException() {
        return result.left();
    }

    /**
     * @return an optional containing the result of this try result if this is a success, an empty optional otherwise
     */
    @NonNull
    public Optional<A> getResult() {
        return result.right();
    }

    /**
     * @param defaultValue the value returned if this is a failure
     * @return the result of this try result if this is a success, <code>defaultValue</code> otherwise
     */
    @NonNull
    public A getResult(@NonNull A defaultValue) {
        return result.right().orElse(defaultValue);
    }

    /**
     * Consume the exception of this result if this is a failure
     *
     * @param failure the consumer consuming the exception of this result if this is a failure
     * @return this
     */
    @NonNull
    public TryResult<A, E> ifFailedAccept(Consumer<? super E> failure) {
        this.getException().ifPresent(failure);
        return this;
    }

    /**
     * Call the provided function if this is a failure on the thrown exception.
     *
     * @param failure the function call if this is a failure
     * @return this
     */
    @NonNull
    public TryResult<A, E> ifFailedApply(Function<E, Nil> failure) {
        this.getException().ifPresent(failure::apply);
        return this;
    }

    /**
     * @return the value of this result if this is not a failure
     * @throws E if this is a failure
     */
    @NonNull
    public A throwIfFailure() throws E {
        return this.getEither().tryMerge(
                e -> { throw e;},
                r -> r);
    }

    /**
     * Cast if possible the exception to the provided exception type
     * @param exceptionType the class of the type of the exception to cast to
     * @param <Y> the type of the exception to cast to
     * @return if this is a success then the returned optional is not empty and contained a new successful TryResult with this result, otherwise an optional
     * containing the exception casted to the provide type if possible, otherwise an empty optional
     */
    public <Y extends Exception> Optional<TryResult<A,Y>> filter(Class<Y> exceptionType) {
        final Function1<A,Optional<TryResult<A,Y>>> successMerger = a -> Optional.of(TryResult.success(a));
        final Function1<E,Optional<TryResult<A,Y>>> failureMerger = e -> FPUtils.as(exceptionType).apply(e).map(TryResult::<A,Y>failure);

        return result.merge(failureMerger,successMerger);
    }


    /**
     *
     * @param exceptionType the class of the exception type to cast to
     * @param <Y> the type of the exception to cast to
     * @return If this is a success, this simply create a new successful optional with this result. Otherwise try to cast the exception
     * to the provided type. If it succeed, returns a new <code>TryResult</code> with the new type, otherwise throws the exception of the try
     * @throws E if this is a failure and the exception of this try cannot be casted to <code>Y</code>
     */
    public <Y extends Exception> TryResult<A,Y> filterOrThrow(Class<Y> exceptionType) throws E {
        return filter(exceptionType).orElseThrow(() -> getException().get());
    }


    /**
     * Applies the provided consumer to the thrown exception if this is a failure and then returns the result or throw an exception
     *
     * @param failure the consumer to apply to the thrown exception
     * @return the result of this try if this is a success
     * @throws E is this is a failure
     */
    @NonNull
    public A acceptIfFailedThenGetWithThrow(Consumer<E> failure) throws E {
        return ifFailedAccept(failure).throwIfFailure();
    }

    /**
     * Applies the provided function to the thrown exception if this is a failure and then returns the result or throw an exception
     *
     * @param failure the function to apply to the thrown exception, the result of the function is ignored
     * @return the result of this try if this is a success
     * @throws E is this is a failure
     */
    @NonNull
    public A applyIfFailedThenGetWithThrow(Function<E,Nil> failure) throws E {
        return ifFailedApply(failure).throwIfFailure();
    }

    /**
     * @return the {@link Either} object used to store the result or the exception
     */
    @NonNull
    public Either<E,A> getEither() {
        return result;
    }

    /**
     * @return the result if this is a success, otherwise wrap the throw exception in a {@link TryException} which is an unchecked exception
     */
    @NonNull
    public A getSuccess() {
        return result.merge(e -> {throw new TryException(e);}, Function1.identity());
    }

    /**
     * Apply a function to the result if this is a success, do nothing otherwise
     * @param f the function use to map the result of this try result
     * @param <B> the type of the result of the provided function
     * @return a new <code>TryResult</code> with its result equals to the application of the provided function to this result (if a success), this otherwise
     */
    public <B> TryResult<B, E> map(Function1<? super A, ? extends B> f) {
        return new TryResult<>(result.map(Function1.identity(),f));
    }

    @NonNull
    public <B> TryResult<B,Throwable> tryMap(Try1<? super A, ? extends B,?> t) {
        final TryResult<? extends B, Throwable> result = getEither().merge(TryResult::failure, t::applySafely);
        return result.map(b -> b);
    }

    @NonNull
    public <B> TryResult<B, E> bind(Function1<? super A, TryResult<B, E>> f) {
        return result.merge(TryResult::failure, f);
    }

    @NonNull
    public <W extends Exception> TryResult<A,W> wrapException(Function1<? super E, ? extends W> exceptionWrapper) {
        return new TryResult<>(result.map(exceptionWrapper,Function1.identity()));
    }
}
