package com.test.game.core.utils;

import java.util.Map;

public class MapBuilder<K, V> {
    private final Map<K, V> map;

    public MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    public MapBuilder<K, V> put(K k, V v) {
        this.map.put(k, v);
        return this;
    }

    public Map<K, V> build() {
        return this.map;
    }
}
