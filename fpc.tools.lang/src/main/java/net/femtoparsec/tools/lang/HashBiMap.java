package net.femtoparsec.tools.lang;

import fpc.tools.lang.BiMap;

public class HashBiMap<K,V> extends BaseBiMap<K,V> {

  private final BiMap<V,K> inverse;

  public HashBiMap() {
    this.inverse = new HashBiMap<>(this);
  }

  public HashBiMap(HashBiMap<V, K> inverse) {
    super(inverse.invert, inverse.direct);
    this.inverse = inverse;
  }

  @Override
  public BiMap<V, K> inverse() {
    return inverse;
  }

}
