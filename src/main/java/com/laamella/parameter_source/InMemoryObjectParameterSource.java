package com.laamella.parameter_source;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class InMemoryObjectParameterSource implements ObjectParameterSource {
    private final Map<String, Object> storage = new HashMap<>();

    @Override
    public <T> Optional<T> getOptionalObject(String key, Class<T> type) {
        requireNonNull(key);
        requireNonNull(type);

        return Optional.ofNullable(type.cast(storage.get(key)));
    }

    public InMemoryObjectParameterSource put(String key, Object o) {
        requireNonNull(key);

        storage.put(key, o);

        return this;
    }
}
