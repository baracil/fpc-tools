package net.femtoparsec.tools.lang;

import fpc.tools.lang.BiMap;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseBiMap<K,V> implements BiMap<K,V> {

  protected final Map<K,V> direct;
  protected final Map<V,K> invert;


  protected BaseBiMap() {
    this.direct = new HashMap<>();
    this.invert = new HashMap<>();
  }

  @Override
  public int size() {
    return direct.size();
  }

  @Override
  public boolean isEmpty() {
    return direct.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return direct.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return invert.containsValue(value);
  }

  @Override
  public V get(Object key) {
    return direct.get(key);
  }

  @Override
  public V put(K key, V value) {
    final var result = direct.put(key, value);
    invert.put(value,key);
    return result;
  }

  @Override
  public @Nullable V remove(Object key) {
    final var removed = direct.remove(key);
    if (removed != null) {
      invert.remove(removed);
    }
    return removed;
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    m.forEach(this::put);
  }

  @Override
  public void clear() {
    direct.clear();
    invert.clear();
  }

  @Override
  public Set<K> keySet() {
    return direct.keySet();
  }

  @Override
  public Set<V> values() {
    return invert.keySet();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return direct.entrySet();
  }
}
