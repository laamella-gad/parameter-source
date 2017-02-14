package com.laamella.parameter_source;

import java.util.Objects;
import java.util.Optional;

import static com.laamella.parameter_source.ParameterSourceException.missingKeyException;
import static java.util.Objects.*;

/**
 * Abstract interface to a parameter (simple key-value) store, adds checking of required parameters, a tight interface,
 * logging, and easy testing.
 */
public interface ParameterSource {
    Optional<String> getOptionalString(String key);

    Optional<Integer> getOptionalInteger(String key);

    default ParameterSource withFallback(ParameterSource fallback) {
        requireNonNull(fallback);
        return new FallbackParameterSource(this, fallback);
    }

    default String getString(String key) {
        requireNonNull(key);
        return getOptionalString(key).orElseThrow(missingKeyException(key));
    }

    default int getInteger(String key) {
        requireNonNull(key);
        return getOptionalInteger(key).orElseThrow(missingKeyException(key));
    }
}
