package com.laamella.parameter_source;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Supports the "withFallback" method.
 * <p>Takes values from primarySource
 * unless that doesn't know the key,
 * then it delegates to fallbackSource.
 * If that doesn't have the key either
 * it will return the usual Optional.empty().
 * <p>This can be chained to create a fallback chain.</p>
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

    public ParameterSource getPrimarySource() {
        return primarySource;
    }

    public ParameterSource getFallbackSource() {
        return fallbackSource;
    }
}
