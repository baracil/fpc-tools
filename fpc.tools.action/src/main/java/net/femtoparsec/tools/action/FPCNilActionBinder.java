package net.femtoparsec.tools.action;

import fpc.tools.action.ActionBinder;
import fpc.tools.action.ActionBinding;
import fpc.tools.action.NilActionBinder;
import fpc.tools.fp.Nil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FPCNilActionBinder implements NilActionBinder {

    private final ActionBinder<Nil> delegate;

    @Override
    public ActionBinding createBinding(Object item) {
        return delegate.createBinding(item,Nil.NULL);
    }
}
