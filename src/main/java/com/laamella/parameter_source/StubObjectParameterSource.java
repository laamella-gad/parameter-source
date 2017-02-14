package com.laamella.parameter_source;

import java.util.Optional;

/**
 * An object parameter source that returns the same object for all keys.
 * Suggested use is testing.
 */
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
