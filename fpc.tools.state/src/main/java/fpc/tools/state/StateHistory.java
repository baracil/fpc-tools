package fpc.tools.state;

import fpc.tools.fp.Function1;
import fpc.tools.fp.Predicate1;
import fpc.tools.lang.Todo;
import lombok.NonNull;
import net.femtoparsec.tools.state.LinkedStateHistory;
import net.femtoparsec.tools.state.TwoStateHistory;

import java.util.function.Function;

public interface StateHistory<S> {

    static <S> StateHistory<S> create(int historyDepth, S initialState) {
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
    default boolean mutateCurrent(Function1<? super S, ? extends S> operator) {
        final S current = this.getCurrent();
        final S newState = operator.apply(current);
        return pushNewState(newState);
    }

    boolean pushNewState(S state);

    S getCurrent();
    S getPrevious();
    S getAt(int depth);

    default Predicate1<Function<? super S, ?>> previousCurrentTester() {
        return Todo.tester(getCurrent(),getPrevious());
    }

    int capacity();
    int size();
}
