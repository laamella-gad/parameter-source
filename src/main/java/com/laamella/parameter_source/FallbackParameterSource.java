package com.laamella.parameter_source;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Supports the "withFallback" method.
 */
public class FallbackParameterSource implements ParameterSource {
    private final ParameterSource primarySource;
    private final ParameterSource fallbackSource;

    FallbackParameterSource(ParameterSource primarySource, ParameterSource fallbackSource) {
        requireNonNull(primarySource);
        requireNonNull(fallbackSource);
        this.primarySource = primarySource;
        this.fallbackSource = fallbackSource;
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        Optional<String> value = primarySource.getOptionalString(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalString(key);
    }

    @Override
    public Optional<List<String>> getOptionalStringList(String key) {
        Optional<List<String>> value = primarySource.getOptionalStringList(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalStringList(key);
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        Optional<Integer> value = primarySource.getOptionalInteger(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalInteger(key);
    }

    @Override
    public Optional<Long> getOptionalLong(String key) {
        Optional<Long> value = primarySource.getOptionalLong(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalLong(key);
    }

    @Override
    public Optional<Float> getOptionalFloat(String key) {
        Optional<Float> value = primarySource.getOptionalFloat(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalFloat(key);
    }

    @Override
    public Optional<Double> getOptionalDouble(String key) {
        Optional<Double> value = primarySource.getOptionalDouble(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalDouble(key);
    }

    @Override
    public Optional<Boolean> getOptionalBoolean(String key) {
        Optional<Boolean> value = primarySource.getOptionalBoolean(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalBoolean(key);
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        Optional<Object> value = primarySource.getOptionalObject(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalObject(key);
    }

    @Override
    public Optional<URL> getOptionalUrl(String key) {
        Optional<URL> value = primarySource.getOptionalUrl(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalUrl(key);
    }

    @Override
    public Optional<URI> getOptionalUri(String key) {
        Optional<URI> value = primarySource.getOptionalUri(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalUri(key);
    }

    /**
     * @return the path separator of the primary source. The separator from the secondary source is ignored, which makes
     * mixing SubParameterSources and FallbackParameterSources tricky.
     */
    @Override
    public String getPathSeparator() {
        return primarySource.getPathSeparator();
    }

    public ParameterSource getPrimarySource() {
        return primarySource;
    }

    public ParameterSource getFallbackSource() {
        return fallbackSource;
    }
}
