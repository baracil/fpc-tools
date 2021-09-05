package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Predicate1;
import fpc.tools.lang.Tools;
import lombok.NonNull;
import net.femtoparsec.tools.state.LinkedStateHistory;
import net.femtoparsec.tools.state.TwoStateHistory;

import java.util.function.Function;

public interface StateHistory<S> {

    static <S> StateHistory<S> create(int historyDepth, @NonNull S initialState) {
        if (historyDepth<=1) {
            throw new IllegalArgumentException("Invalid depth, must be at least 2 : "+historyDepth);
        } else if (historyDepth == 2) {
            return new TwoStateHistory<>(initialState);
        } else {
            return new LinkedStateHistory<>(historyDepth, initialState);
        }
    }

    /**
     * @param operator the operator to apply to the current state to get the next state
     * @return true if the mutation changed the state, false otherwise
     */
    default boolean mutateCurrent(@NonNull Function1<? super S, ? extends S> operator) {
        final S current = this.getCurrent();
        final S newState = operator.f(current);
        return pushNewState(newState);
    }

    boolean pushNewState(@NonNull S state);

    @NonNull S getCurrent();
    @NonNull S getPrevious();
    @NonNull S getAt(int depth);

    default @NonNull Predicate1<Function<? super S, ?>> previousCurrentTester() {
        return Tools.tester(getCurrent(),getPrevious());
    }

    int capacity();
    int size();
}
