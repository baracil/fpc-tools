package fpc.tools.fp;


import jakarta.annotation.Nullable;

import java.util.Optional;

/**
 * Holder either a A or a B value but never both. There are only two kinds of either i) left either, ii) right either.
 * You can get the type of an Either with {@link #isLeft()} or {@link #isRight()}. Both previous methods return different result, always.
 * <p>
 * You can get the left or right value of this either by using {@link #left()}
 * and {@link #right()}. Only one of the previous methods return an non empty optional.
 *
 * @author Bastien Aracil
 */
public final class Either<A, B> {

  private final @Nullable A left;
  private final @Nullable B right;

  public Either(@Nullable A left, @Nullable B right) {
    assert (left == null) != (right == null) : "One and only one of left or right must be non null";
    this.left = left;
    this.right = right;
  }

  public static <A, B> Either<A, B> right(B b) {
    return new Either<>(null, b);
  }

  public static <A, B> Either<A, B> left(A a) {
    return new Either<>(a, null);
  }

  /**
   * Create a left {@link Either} if the given optional is not empty, otherwise a right Either with {@link Nil} value
   *
   * @param optional
   * @param <A>
   * @return
   */
  public static <A> Either<A, Nil> with(Optional<A> optional) {
    return optional.map(Either::<A, Nil>left).orElse(Either.right(Nil.NULL));
  }

  public boolean isLeft() {
    return left != null;
  }

  public boolean isRight() {
    return right != null;
  }

  public Optional<A> left() {
    return Optional.ofNullable(left);
  }

  public Optional<B> right() {
    return Optional.ofNullable(right);
  }

  /**
   * Transform this either to another one by applying one of the provided function
   *
   * @param leftMapper  function applied if this is a left either
   * @param rightMapper function applied if this is a right either
   * @return an either with the same side as this but after applying the corresponding provided function
   */
  public <C, D> Either<C, D> map(Function1<? super A, ? extends C> leftMapper, Function1<? super B, ? extends D> rightMapper) {
    if (left != null) {
      return Either.left(leftMapper.apply(left));
    } else if (right != null) {
      return Either.right(rightMapper.apply(right));
    } else {
      throw new IllegalStateException("One and only one of left or right must be non null");
    }
  }

  public <C, D> Either<C, D> flatMap(Function1<? super A, Either<C, D>> leftMapper, Function1<? super B, Either<C, D>> rightMapper) {
    if (left != null) {
      return leftMapper.apply(left);
    } else if (right != null) {
      return rightMapper.apply(right);
    } else {
      throw new IllegalStateException("One and only one of left or right must be non null");
    }
  }

  /**
   * Merge this either to a single value by using one of the provided function
   *
   * @param leftMapper  function used if this is a left either
   * @param rightMapper function used if this is a right either
   * @param <C>         the type of the result
   * @return <code>left.apply(this.left().get())</code> if this is a left either, <code>right.apply(this.right().get())</code> if this is a right either
   */
  public <C> C merge(Function1<? super A, ? extends C> leftMapper, Function1<? super B, ? extends C> rightMapper) {
    if (left != null) {
      return leftMapper.apply(left);
    } else if (right != null) {
      return rightMapper.apply(right);
    } else {
      throw new IllegalStateException("One and only one of left or right must be non null");
    }
  }

  public <C, T extends Throwable> C tryMerge(Try1<? super A, ? extends C, ? extends T> leftMapper,
                                             Try1<? super B, ? extends C, ? extends T> rightMapper) throws T {
    if (left != null) {
      return leftMapper.apply(left);
    } else if (right != null) {
      return rightMapper.apply(right);
    } else {
      throw new IllegalStateException("One and only one of left or right must be non null");
    }
  }

  public void acceptMerge(Consumer1<? super A> leftConsumer, Consumer1<? super B> rightConsumer) {
    if (left != null) {
      leftConsumer.accept(left);
    } else if (right != null) {
      rightConsumer.accept(right);
    } else {
      throw new IllegalStateException("One and only one of left or right must be non null");
    }
  }

  public B toRightValue(Function1<? super A, ? extends B> switcher) {
    if (left != null) {
      return switcher.apply(left);
    } else if (right != null) {
      return right;
    } else {
      throw new IllegalStateException("One and only one of left or right must be non null");
    }
  }

  public A toLeftValue(Function1<? super B, ? extends A> switcher) {
    if (right != null) {
      return switcher.apply(right);
    } else if (left != null) {
      return left;
    } else {
      throw new IllegalStateException("One and only one of left or right must be non null");
    }
  }
}
