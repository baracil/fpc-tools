package fpc.tools.state;

import fpc.tools.fp.Predicate1;
import fpc.tools.lang.Tools;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.function.Function;

@RequiredArgsConstructor
public class Transition<S> implements Predicate1<Function<? super S, ?>> {

    @Getter
    private final @NonNull S current;

    @Getter
    private final @NonNull S previous;

    public boolean isNop() {
        return Objects.equals(current,previous);
    }

    @Override
    public boolean f(@NonNull Function<? super S, ?> function) {
        return Tools.same(previous,current,function);
    }
}
