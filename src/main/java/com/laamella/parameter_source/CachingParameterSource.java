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
    private final Cache storage = new Cache();
    private final ParameterSource cachedParameterSource;

    public CachingParameterSource(ParameterSource cachedParameterSource) {
        this.cachedParameterSource = cachedParameterSource;
    }

    private static class Cache {
        private final Map<String, Object> content = new HashMap<>();

        public <T> T get(String key, Function<String, T> freshValueSource) {
            return (T) content.computeIfAbsent(key, freshValueSource);
        }

        public void flush() {
            content.clear();
        }
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        return storage.get(key, cachedParameterSource::getOptionalString);
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        return storage.get(key, cachedParameterSource::getOptionalInteger);
    }

    @Override
    public Optional<Long> getOptionalLong(String key) {
        return storage.get(key, cachedParameterSource::getOptionalLong);
    }

    @Override
    public Optional<Float> getOptionalFloat(String key) {
        return storage.get(key, cachedParameterSource::getOptionalFloat);
    }

    @Override
    public Optional<Double> getOptionalDouble(String key) {
        return storage.get(key, cachedParameterSource::getOptionalDouble);
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        return storage.get(key, cachedParameterSource::getOptionalObject);
    }

    public void flush() {
        storage.flush();
    }
}
