package fpc.tools.lang;

import net.femtoparsec.tools.lang.HashBiMap;

import java.util.Map;
import java.util.Set;

public interface BiMap<K,V> extends Map<K,V> {


  BiMap<V,K> inverse();

  @Override
  Set<V> values();


  static <K,V> BiMap<K,V> createHashBiMap() {
    return new HashBiMap<>();
  }

}
