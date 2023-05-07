package net.femtoparsec.tools.action;

import fpc.tools.action.*;
import fpc.tools.fp.TryResult;
import fpc.tools.lang.ThrowableTool;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Slf4j
public class FPCActionExecutor implements ActionExecutor {

  public static final boolean TRACE_ACTIONS = Boolean.getBoolean("action.trace");

  private final AtomicLong actionId = new AtomicLong(0);

  private final Executor executor;

  private final ActionProvider actionProvider;

  private final List<ActionFilter> actionFilters;

  private final ActionSpyDispatcher dispatcher = new ActionSpyDispatcher();

  @Override
  public void addActionSpy(ActionSpy actionSpy) {
    dispatcher.addActionSpy(actionSpy);
  }

  @Override
  public void removeActionSpy(ActionSpy actionSpy) {
    dispatcher.removeActionSpy(actionSpy);
  }

  @Override
  public <P, R> CompletionStage<R> pushAction(
      Class<? extends Action<? super P, ? extends R>> actionClass,
      P parameter) {
    try {
      final Action<? super P, ? extends R> action = actionProvider.getAction(actionClass);
      return this.pushAction(action, parameter);
    } catch (Throwable t) {
      ThrowableTool.interruptIfCausedByInterruption(t);
      return CompletableFuture.failedFuture(t);
    }
  }

  @Override
  public <P, R> CompletionStage<R> pushAction(Action<? super P, ? extends R> action,
                                                       P parameter) {
    final long id = actionId.getAndIncrement();
    dispatcher.onPushedAction(this, id, action, parameter);
    return this.<P, R>doPushAction(action, parameter).whenComplete((r, t) -> {
      final TryResult<Throwable, R> result = r != null ? TryResult.success(r) : TryResult.failure(t);
      dispatcher.onActionResult(this, id, result);
    });
  }

  public <P, R> CompletionStage<R> doPushAction(Action<? super P, ? extends R> action,
                                                         P parameter) {
    final CompletableFuture<R> result = new CompletableFuture<>();
    final ActionItem<P, R> actionItem = new ActionItem<>(action, parameter, result);
    try {
      final Runnable runnable = () -> this.performAction(actionItem);
      if (action.isAsync()) {
        executor.executeAsync(runnable);
      } else {
        executor.executeSync(runnable);
      }
    } catch (Throwable t) {
      ThrowableTool.interruptIfCausedByInterruption(t);
      actionItem.completeExceptionally(t);
    }
    return result;
  }


  private <P, R> void performAction(ActionItem<P, R> ticket) {
    final Runnable execution;
    if (TRACE_ACTIONS) {
      execution = wrapWithTrace(ticket, createExecution(ticket));
    } else {
      execution = createExecution(ticket);
    }
    ticket.executeIfNotCompleted(execution);
  }

  private Runnable wrapWithTrace(ActionItem<?, ?> ticket, Runnable execution) {
    return () -> {
      final long start = System.nanoTime();
      final String actionName = ticket.getAction().getClass().getSimpleName();
      System.out.format("## %7s action '%s'%n", "launch", actionName);
      try {
        execution.run();
        final long last = System.nanoTime() - start;
        System.out.format("## %7s action '%s' : %.3f ms %n", "done", actionName, last / 1e6);
      } catch (Throwable t) {
        System.out.format("## %7s action '%s' : %s%n", "fail", actionName, t.getMessage());
      }
    };
  }

  private <P, R> Runnable createExecution(ActionItem<P, R> ticket) {
    return () -> {
      final P parameter = ticket.getParameter();
      Action<? super P, ? extends R> effectiveAction = ticket.getAction();
      try {
        for (ActionFilter actionFilter : actionFilters) {
          effectiveAction = actionFilter.preProcessAction(effectiveAction, parameter);
        }

        final R result = effectiveAction.execute(parameter);

        postProcess(effectiveAction, TryResult.success(result));
        ticket.completeWith(result);

      } catch (Throwable t) {
        LOG.warn("Action {} failed : {}", ticket.getAction().getClass().getSimpleName(), t.getMessage());
        if (LOG.isDebugEnabled()) {
          LOG.debug("Stacktrace ", t);
        }
        ThrowableTool.interruptIfCausedByInterruption(t);
        postProcess(effectiveAction, TryResult.failure(t));
        ticket.completeExceptionally(t);
      }
    };
  }

  private <P, R> void postProcess(Action<? super P, ? extends R> action,
                                  TryResult<Throwable, R> result) {
    final var iter = actionFilters.listIterator(actionFilters.size());
    while (iter.hasPrevious()) {
      iter.previous().postProcessAction(action, result);
    }
  }
}
