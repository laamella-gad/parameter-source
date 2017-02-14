package com.laamella.parameter_source;

import java.util.Optional;

import static com.laamella.parameter_source.ParameterSourceException.missingKeyException;

/**
 * Abstract interface to a parameter (simple key-value) store, adds checking of required parameters, a tight interface,
 * logging, and easy testing.
 */
public interface ParameterSource {
    default String getString(String key) {
        return getOptionalString(key).orElseThrow(missingKeyException(key));
    }

    Optional<String> getOptionalString(String key);

    default int getInteger(String key) {
        return getOptionalInteger(key).orElseThrow(missingKeyException(key));
    }

    Optional<Integer> getOptionalInteger(String key);
}
