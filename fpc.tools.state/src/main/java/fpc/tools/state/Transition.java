package fpc.tools.state;

import fpc.tools.fp.Predicate1;
import fpc.tools.lang.Todo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.function.Function;

@RequiredArgsConstructor
public class Transition<S> implements Predicate1<Function<? super S, ?>> {

    @Getter
    private final S current;

    @Getter
    private final S previous;

    public boolean isNop() {
        return Objects.equals(current,previous);
    }

    @Override
    public boolean test(Function<? super S, ?> function) {
        return Todo.same(previous,current,function);
    }
}
