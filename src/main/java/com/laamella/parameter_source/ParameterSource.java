package com.laamella.parameter_source;

import java.util.Optional;

import static com.laamella.parameter_source.ParameterSourceException.missingKeyException;
import static java.util.Objects.requireNonNull;

/**
 * Abstract interface to a parameter (simple key-value) store, adds checking of required parameters, a tight interface,
 * logging, and easy testing.
 */
public interface ParameterSource {
    /**
     * Retrieves a String from this source by key.
     */
    Optional<String> getOptionalString(String key);

    /**
     * Retrieves an optional Integer from this source by key.
     */
    Optional<Integer> getOptionalInteger(String key);

    /**
     * Creates a parameter source that takes its values from this
     * parameter source.
     * If values are missing, it looks in the fallback instead.
     * @see FallbackParameterSource
     */
    default FallbackParameterSource withFallback(ParameterSource fallback) {
        requireNonNull(fallback);
        return new FallbackParameterSource(this, fallback);
    }

    /**
     * Retrieves a required String from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default String getString(String key) {
        requireNonNull(key);
        return getOptionalString(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required int from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default int getInteger(String key) {
        requireNonNull(key);
        return getOptionalInteger(key).orElseThrow(missingKeyException(key));
    }
}
