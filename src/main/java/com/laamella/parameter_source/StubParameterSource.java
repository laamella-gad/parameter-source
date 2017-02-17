package com.laamella.parameter_source;

import java.util.Optional;

/**
 * An object parameter source that returns the same object for all keys.
 * Suggested use is testing.
 */
public class StubParameterSource implements ParameterSource {
    private Object stubValue;

    public StubParameterSource(Object stubValue) {
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
