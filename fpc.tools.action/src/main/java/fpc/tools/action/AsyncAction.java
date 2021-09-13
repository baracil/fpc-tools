package fpc.tools.action;

import java.util.concurrent.CompletionStage;

public interface AsyncAction<P,R> extends Action<P, CompletionStage<R>>{
}
