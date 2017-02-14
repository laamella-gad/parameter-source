package com.laamella.parameter_source;

import java.util.Optional;

public class StubObjectParameterSource implements ObjectParameterSource {
    private final Object stubValue;

    public StubObjectParameterSource(Object stubValue) {
        this.stubValue = stubValue;
    }

    @Override
    public <T> Optional<T> getOptionalObject(String key, Class<T> type) {
        return Optional.ofNullable(type.cast(stubValue));
    }
}
