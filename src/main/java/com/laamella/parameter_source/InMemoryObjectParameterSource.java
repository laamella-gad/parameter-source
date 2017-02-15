package com.laamella.parameter_source;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * This source has no external storage.
 * It only contains key-value pairs that have been put in using the "put" method.
 */
public class InMemoryObjectParameterSource extends ObjectParameterSource {
    private final Map<String, Object> storage = new HashMap<>();

    @Override
    public Optional<Object> getOptionalObject(String key) {
        requireNonNull(key);

        return Optional.ofNullable(storage.get(key));
    }

    public InMemoryObjectParameterSource put(String key, Object o) {
        requireNonNull(key);

        storage.put(key, o);

        return this;
    }
}
