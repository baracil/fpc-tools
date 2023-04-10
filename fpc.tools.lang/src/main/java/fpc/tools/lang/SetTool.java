package fpc.tools.lang;

import lombok.NonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetTool {

  public static <T> @NonNull Set<T> difference(@NonNull Set<T> set1, @NonNull Collection<T> set2) {
    final var result = new HashSet<>(set1);
    set2.forEach(result::remove);
    return result;
  }
 }
