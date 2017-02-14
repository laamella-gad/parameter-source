package com.laamella.parameter_source;

import java.util.Optional;

import static com.laamella.parameter_source.ParameterSourceException.missingKeyException;
import static java.util.Objects.requireNonNull;

/**
 * A parameter source that stores objects.
 */
public interface ObjectParameterSource extends ParameterSource {
    default Optional<String> getOptionalString(String key) {
        return getOptionalObject(key, String.class);
    }

    default Optional<Integer> getOptionalInteger(String key) {
        return getOptionalObject(key, int.class);
    }

    /**
     * Gets an object from the source of class "type".
     */
    default <T> T getObject(String key, Class<T> type) {
        requireNonNull(key);
        requireNonNull(type);
        return getOptionalObject(key, type).orElseThrow(missingKeyException(key));
    }

    /**
     * Gets an object from the source of class "type".
     * If the key exists but the value is not of type "type",
     * the result will be Optional.empty().
     */
    <T> Optional<T> getOptionalObject(String key, Class<T> type);
}
