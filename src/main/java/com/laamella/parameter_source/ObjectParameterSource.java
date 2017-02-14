package com.laamella.parameter_source;

import java.util.Optional;

import static com.laamella.parameter_source.ParameterSourceException.missingKeyException;

public interface ObjectParameterSource extends ParameterSource {
    default Optional<String> getOptionalString(String key) {
        return getOptionalObject(key, String.class);
    }

    default Optional<Integer> getOptionalInteger(String key) {
        return getOptionalObject(key, int.class);
    }

    default <T> T getObject(String key, Class<T> type) {
        return getOptionalObject(key, type).orElseThrow(missingKeyException(key));
    }

    <T> Optional<T> getOptionalObject(String key, Class<T> type);
}
