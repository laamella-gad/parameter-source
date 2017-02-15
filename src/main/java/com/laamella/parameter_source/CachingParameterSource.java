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
    private final Cache<ObjectKey, Optional<?>> objectValueCache;

    private static final class ObjectKey {
        public final String key;
        public final Class<?> type;

        public ObjectKey(String key, Class<?> type) {
            this.key = key;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ObjectKey objectKey = (ObjectKey) o;

            if (!key.equals(objectKey.key)) return false;
            return type.equals(objectKey.type);
        }

        @Override
        public int hashCode() {
            int result = key.hashCode();
            result = 31 * result + type.hashCode();
            return result;
        }
    }

    public CachingParameterSource(ParameterSource cachedParameterSource) {
        stringValueCache = new Cache<>(cachedParameterSource::getOptionalString);
        integerValueCache = new Cache<>(cachedParameterSource::getOptionalInteger);
        objectValueCache = new Cache<>((ok) -> cachedParameterSource.getOptionalObject(ok.key, ok.type));
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
    public <T> Optional<T> getOptionalObject(String key, Class<T> type) {
        return (Optional<T>) objectValueCache.get(new ObjectKey(key, type));
    }

    public void flush() {
        stringValueCache.flush();
        integerValueCache.flush();
        objectValueCache.flush();
    }
}
