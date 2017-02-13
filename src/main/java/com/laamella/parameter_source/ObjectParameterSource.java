package com.laamella.parameter_source;

import java.util.Optional;

public abstract class ObjectParameterSource extends ParameterSource {
    @Override
    public Optional<String> getOptionalString(String key) {
        return getOptionalObject(key, String.class);
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        return getOptionalObject(key, int.class);
    }

    public <T> T getObject(String key, Class<T> type) {
        return getOptionalObject(key, type).orElseThrow(missingKeyException(key));
    }

    public abstract <T> Optional<T> getOptionalObject(String key, Class<T> type);
}
