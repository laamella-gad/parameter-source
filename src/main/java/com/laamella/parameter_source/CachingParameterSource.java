package com.laamella.parameter_source;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Basic caching for any parameter source.
 * Keeps values in cache until flush() is called.
 */
public class CachingParameterSource implements ParameterSource {
    private final Cache<String, Optional<String>> stringValueCache;
    private final Cache<String, Optional<Integer>> integerValueCache;
    private final Cache<String, Optional<Object>> objectValueCache;

    public CachingParameterSource(ParameterSource cachedParameterSource) {
        stringValueCache = new Cache<>(cachedParameterSource::getOptionalString);
        integerValueCache = new Cache<>(cachedParameterSource::getOptionalInteger);
        objectValueCache = new Cache<>(cachedParameterSource::getOptionalObject);
    }

    private static class Cache<K, V> {
        private final Map<K, V> content = new HashMap<K, V>();
        private final Function<K, V> freshValueSource;

        public Cache(Function<K, V> freshValueSource) {
            this.freshValueSource = freshValueSource;
        }

        public V get(K key) {
            return content.computeIfAbsent(key, freshValueSource);
        }

        public void flush() {
            content.clear();
        }
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        return stringValueCache.get(key);
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        return integerValueCache.get(key);
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        return objectValueCache.get(key);
    }

    public void flush() {
        stringValueCache.flush();
        integerValueCache.flush();
        objectValueCache.flush();
    }
}
