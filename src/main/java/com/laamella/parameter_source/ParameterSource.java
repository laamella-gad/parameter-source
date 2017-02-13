package com.laamella.parameter_source;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Abstract interface to a parameter (simple key-value) store, adds checking of required parameters, a tight interface,
 * logging, and easy testing.
 */
public abstract class ParameterSource {
    public String getString(String key) {
        return getOptionalString(key).orElseThrow(missingKeyException(key));
    }

    public abstract Optional<String> getOptionalString(String key);

    public int getInteger(String key) {
        return getOptionalInteger(key).orElseThrow(missingKeyException(key));
    }

    public abstract Optional<Integer> getOptionalInteger(String key);

    protected Supplier<ParameterSourceException> missingKeyException(String key) {
        return () -> new ParameterSourceException("Key %s is missing.", key);
    }
}
