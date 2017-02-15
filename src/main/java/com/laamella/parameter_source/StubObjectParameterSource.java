package com.laamella.parameter_source;

import java.util.Optional;

/**
 * An object parameter source that returns the same object for all keys.
 * Suggested use is testing.
 */
public class StubObjectParameterSource implements ObjectParameterSource {
    private Object stubValue;

    public StubObjectParameterSource(Object stubValue) {
        this.stubValue = stubValue;
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        return Optional.ofNullable(stubValue);
    }

    public void setStubValue(Object stubValue) {
        this.stubValue = stubValue;
    }
}
