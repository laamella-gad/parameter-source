package com.laamella.parameter_source;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class FallbackParameterSource implements ParameterSource {
    private final ParameterSource primary;
    private final ParameterSource fallback;

    public FallbackParameterSource(ParameterSource primary, ParameterSource fallback) {
        requireNonNull(primary);
        requireNonNull(fallback);
        this.primary = primary;
        this.fallback = fallback;
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        Optional<String> value = primary.getOptionalString(key);
        if (value.isPresent()) {
            return value;
        }
        return fallback.getOptionalString(key);
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        Optional<Integer> value = primary.getOptionalInteger(key);
        if (value.isPresent()) {
            return value;
        }
        return fallback.getOptionalInteger(key);
    }
}
