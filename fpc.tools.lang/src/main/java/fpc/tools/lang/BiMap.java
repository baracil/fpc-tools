package fpc.tools.lang;

import lombok.NonNull;
import net.femtoparsec.tools.lang.HashBiMap;

import java.util.Map;
import java.util.Set;

public interface BiMap<K,V> extends Map<K,V> {


  @NonNull BiMap<V,K> inverse();

  @Override
  @NonNull Set<V> values();


  static <K,V> @NonNull BiMap<K,V> createHashBiMap() {
    return new HashBiMap<>();
  }

}
