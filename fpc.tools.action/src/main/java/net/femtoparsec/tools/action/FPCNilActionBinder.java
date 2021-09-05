package net.femtoparsec.tools.action;

import fpc.tools.action.ActionBinder;
import fpc.tools.action.ActionBinding;
import fpc.tools.action.NilActionBinder;
import fpc.tools.fp.Nil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FPCNilActionBinder implements NilActionBinder {

    @NonNull
    private final ActionBinder<Nil> delegate;

    @Override
    public @NonNull ActionBinding createBinding(@NonNull Object item) {
        return delegate.createBinding(item,Nil.NULL);
    }
}
