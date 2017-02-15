package com.laamella.parameter_source;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Supports the "withFallback" method.
 */
public class FallbackParameterSource implements ParameterSource {
    private final ParameterSource primarySource;
    private final ParameterSource fallbackSource;

    public FallbackParameterSource(ParameterSource primarySource, ParameterSource fallbackSource) {
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
    public Optional<Integer> getOptionalInteger(String key) {
        Optional<Integer> value = primarySource.getOptionalInteger(key);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalInteger(key);
    }

    @Override
    public <T> Optional<T> getOptionalObject(String key, Class<T> type) {
        Optional<T> value = primarySource.getOptionalObject(key, type);
        if (value.isPresent()) {
            return value;
        }
        return fallbackSource.getOptionalObject(key, type);
    }

    public ParameterSource getPrimarySource() {
        return primarySource;
    }

    public ParameterSource getFallbackSource() {
        return fallbackSource;
    }
}
