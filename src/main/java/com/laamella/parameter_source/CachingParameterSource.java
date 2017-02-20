package com.laamella.parameter_source;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

/**
 * Basic caching for any parameter source.
 */
public class CachingParameterSource implements ParameterSource {
    private final Cache cache;
    private final ParameterSource cachedParameterSource;

    public CachingParameterSource(ParameterSource cachedParameterSource) {
        this(cachedParameterSource, Duration.ofSeconds(30));
    }

    public CachingParameterSource(ParameterSource cachedParameterSource, Duration flushInterval) {
        this(cachedParameterSource, new Cache.Default(flushInterval));
    }

    public CachingParameterSource(ParameterSource cachedParameterSource, Cache cache) {
        this.cachedParameterSource = cachedParameterSource;
        this.cache = cache;
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        return cache.get(key, cachedParameterSource::getOptionalString);
    }

    @Override
    public Optional<List<String>> getOptionalStringList(String key) {
        return cache.get(key, cachedParameterSource::getOptionalStringList);
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        return cache.get(key, cachedParameterSource::getOptionalInteger);
    }

    @Override
    public Optional<Long> getOptionalLong(String key) {
        return cache.get(key, cachedParameterSource::getOptionalLong);
    }

    @Override
    public Optional<Float> getOptionalFloat(String key) {
        return cache.get(key, cachedParameterSource::getOptionalFloat);
    }

    @Override
    public Optional<Double> getOptionalDouble(String key) {
        return cache.get(key, cachedParameterSource::getOptionalDouble);
    }

    @Override
    public Optional<Boolean> getOptionalBoolean(String key) {
        return cache.get(key, cachedParameterSource::getOptionalBoolean);
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        return cache.get(key, cachedParameterSource::getOptionalObject);
    }

    @Override
    public Optional<Class<?>> getOptionalClass(String key) {
        return cache.get(key, cachedParameterSource::getOptionalClass);
    }

    @Override
    public <T extends Enum<?>> Optional<T> getOptionalEnum(String key, Class<T> enumType) {
        return cache.get(key, k -> cachedParameterSource.getOptionalEnum(k, enumType));
    }

    @Override
    public Optional<URL> getOptionalUrl(String key) {
        return cache.get(key, cachedParameterSource::getOptionalUrl);
    }

    @Override
    public Optional<URI> getOptionalUri(String key) {
        return cache.get(key, cachedParameterSource::getOptionalUri);
    }

    public void flush() {
        cache.flush();
    }
}
