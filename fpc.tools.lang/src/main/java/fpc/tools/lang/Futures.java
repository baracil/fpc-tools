package fpc.tools.lang;

import lombok.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Futures {

    public static <T> CompletionStage<T> join(CompletionStage<T> from, CompletableFuture<T> to) {
        return from.whenComplete((result,error) -> {
          if (error != null) {
              to.completeExceptionally(error);
          } else {
              to.complete(result);
          }
        });
    }
}
