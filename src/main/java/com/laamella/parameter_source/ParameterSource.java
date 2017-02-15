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
     * Retrieves an optional Object from this source by key.
     */
    Optional<Object> getOptionalObject(String key);

    /**
     * Creates a parameter source that takes its values from this
     * parameter source.
     * If values are missing, it looks in the fallback instead.
     * <p>This can be chained to create a fallback chain.
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

    /**
     * Retrieves a required object from this source by key.
     */
    default Object getObject(String key) {
        requireNonNull(key);
        return getOptionalObject(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Creates a parameter source that prepends "keyPart" to every key requested.
     * This can be chained to go deeper and deeper.
     * Note that a "." between "keyPart" and requested keys is enforced.
     */
    default SubParameterSource subSource(String keyPart) {
        requireNonNull(keyPart);
        return new SubParameterSource(this, keyPart, (a, b) -> {
            while (!a.isEmpty() && a.endsWith(getPathSeparator())) {
                a = a.substring(0, a.length() - 1);
            }
            while (!b.isEmpty() && b.startsWith(getPathSeparator())) {
                b = b.substring(1);
            }
            if (a.isEmpty()) {
                return b;
            }
            if (b.isEmpty()) {
                return a;
            }
            return a + getPathSeparator() + b;
        });
    }

    /**
     * @return the String that separates the parts of a key into a hierarchy.
     */
    default String getPathSeparator() {
        return "/";
    }
}
