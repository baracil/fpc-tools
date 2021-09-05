package net.femtoparsec.tools.validation;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Predicate1;
import fpc.tools.validation.*;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractValidator<O,V extends Validator<O,V>> implements Validator<O,V> {

    @NonNull
    private final ValidationContext context;

    private final String fieldName;

    protected final O value;

    public AbstractValidator(@NonNull ValidationContext context,
                             @NonNull String fieldName, O value) {
        this.context = context;
        this.fieldName = fieldName;
        this.value = value;
        context.addValidatedField(fieldName);
    }

    @Override
    public @NonNull V isNotNull() {
        if (value == null) {
            addError(ErrorType.NOT_NULL_REQUIRED);
        }
        return getThis();
    }

    protected abstract V getThis();

    @Override
    public @NonNull V errorIf(@NonNull Predicate1<O> test, @NonNull String errorType) {
        if (value != null && test.test(value)) {
            addError(errorType);
        }
        return getThis();
    }

    @Override
    public @NonNull V errorIfNot(@NonNull Predicate1<O> test, @NonNull String errorType) {
        if (value != null && !test.test(value)) {
            addError(errorType);
        }
        return getThis();
    }

    @NonNull
    public V addError(@NonNull String errorType) {
        context.addError(fieldName,errorType);
        return getThis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull Optional<O> getValidValue() {
        if (isValid()) {
            return Optional.ofNullable(value);
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NonNull O getValue() {
        if (isValid() && value != null) {
            return value;
        } else {
            throw new IllegalStateException("Invalid value");
        }
    }

    @Override
    public boolean isValid() {
        return context.isFieldValid(fieldName);
    }

    @Override
    public <T, V2 extends Validator<T, V2>> @NonNull V2 map(@NonNull Function1<? super O, ? extends T> mapper,
                                                           @NonNull ValidatorFactory<? super T, ? extends V2> factory) {
        final Function<T, V2> f = t -> factory.create(context, fieldName, t);

        if (value == null) {
            return f.apply(null);
        } else {
            try {
                final T converted = mapper.f(value);
                return f.apply(converted);
            } catch (Throwable t) {
                return f.apply(null).addError(ErrorType.INVALID_VALUE);
            }
        }
    }

    @Override
    public @NonNull <T> Validator<T, ?> map(@NonNull Function1<? super O, ? extends T> mapper) {
        return map(mapper, new ValidatorFactory<T, FPCValidator<T>>() {
            @NonNull
            @Override
            public FPCValidator<T> create(@NonNull ValidationContext context, @NonNull String fieldName, T value) {
                return new FPCValidator<>(context,fieldName,value);
            }
        });
    }

    @Override
    public @NonNull PathValidator toPathValidator(Function1<? super O, ? extends Path> pathBuilder) {
        return map(pathBuilder, FPCPathValidator::new);
    }

}
