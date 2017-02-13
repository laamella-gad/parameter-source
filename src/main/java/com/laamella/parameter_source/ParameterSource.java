package com.laamella.parameter_source;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Abstract interface to a parameter (simple key-value) store, adds checking of required parameters, a tight interface,
 * logging, and easy testing.
 */
public abstract class ParameterSource<T> {
    public String getString(String key) {
        return getOptionalString(key).orElseThrow(missingKeyException(key));
    }

    public Optional<String> getOptionalString(String key) {
        return getOptionalValueFromSource(key).map(Object::toString);
    }

    private int getInteger(String key) {
        return getOptionalInteger(key).orElseThrow(missingKeyException(key));
    }

    private Optional<Integer> getOptionalInteger(String key) {
        final Optional<String> str = getOptionalString(key);
        try {
            return str.map(Integer::parseInt);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not an integer.", str.get(), key);
        }
    }

    /**
     * Get an optional value from the source. This is what all other methods are based on.
     *
     * @param key the name of the parameter
     * @return the value of the parameter wrapped in Optional, or Optional.empty() when it was not found.
     */
    protected abstract Optional<T> getOptionalValueFromSource(String key);

    protected Supplier<ParameterSourceException> missingKeyException(String key) {
        return () -> new ParameterSourceException("Key %s is missing.", key);
    }
}
