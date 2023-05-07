package net.femtoparsec.tools.validation;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Predicate1;
import fpc.tools.validation.*;
import jakarta.annotation.Nullable;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;

public abstract class AbstractValidator<O, V extends Validator<O, V>> implements Validator<O, V> {

  private final ValidationContext context;

  private final String fieldName;

  protected final @Nullable O value;

  public AbstractValidator(ValidationContext context,
                           String fieldName, @Nullable O value) {
    this.context = context;
    this.fieldName = fieldName;
    this.value = value;
    context.addValidatedField(fieldName);
  }

  @Override
  public V isNotNull() {
    if (value == null) {
      addError(ErrorType.NOT_NULL_REQUIRED);
    }
    return getThis();
  }

  protected abstract V getThis();

  @Override
  public V errorIf(Predicate1<O> test, String errorType) {
    if (value != null && test.test(value)) {
      addError(errorType);
    }
    return getThis();
  }

  @Override
  public V errorIfNot(Predicate1<O> test, String errorType) {
    if (value != null && !test.test(value)) {
      addError(errorType);
    }
    return getThis();
  }

  public V addError(String errorType) {
    context.addError(fieldName, errorType);
    return getThis();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<O> getValidValue() {
    if (isValid()) {
      return Optional.ofNullable(value);
    }
    return Optional.empty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public O getValue() {
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
  public <T, V2 extends Validator<T, V2>> V2 map(Function1<? super O, ? extends T> mapper,
                                                 ValidatorFactory<? super T, ? extends V2> factory) {
    final Function<T, V2> f = value -> (V2)factory.create(context, fieldName, value);

    if (value == null) {
      return f.apply(null);
    } else {
      try {
        final T converted = mapper.apply(value);
        return f.apply(converted);
      } catch (Throwable t) {
        return f.apply(null).addError(ErrorType.INVALID_VALUE);
      }
    }
  }

  @Override
  public <T> Validator<T, ?> map(Function1<? super O, ? extends T> mapper) {
    return map(mapper, new ValidatorFactory<T, FPCValidator<T>>() {
      @Override
      public FPCValidator<T> create(ValidationContext context, String fieldName, T value) {
        return new FPCValidator<>(context, fieldName, value);
      }
    });
  }

  @Override
  public PathValidator toPathValidator(Function1<? super O, ? extends Path> pathBuilder) {
    return map(pathBuilder, FPCPathValidator::new);
  }

}
