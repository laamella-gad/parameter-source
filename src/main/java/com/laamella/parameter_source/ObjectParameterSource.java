package com.laamella.parameter_source;

import java.util.Optional;

import static com.laamella.parameter_source.ParameterSourceException.missingKeyException;
import static java.util.Objects.requireNonNull;

/**
 * A parameter source that stores objects.
 * If a retrieve value is not of the requested type,
 * conversion will be attempted.
 */
public interface ObjectParameterSource extends ParameterSource {
    default Optional<String> getOptionalString(String key) {
        return getOptionalObject(key, String.class);
    }

    default Optional<Integer> getOptionalInteger(String key) {
        return getOptionalObject(key, int.class);
    }
}
