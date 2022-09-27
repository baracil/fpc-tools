package fpc.tools.lang;

import lombok.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Futures {

    public static <T> @NonNull CompletionStage<T> join(@NonNull CompletionStage<T> from, @NonNull CompletableFuture<T> to) {
        return from.whenComplete((result,error) -> {
          if (error != null) {
              to.completeExceptionally(error);
          } else {
              to.complete(result);
          }
        });
    }
}
