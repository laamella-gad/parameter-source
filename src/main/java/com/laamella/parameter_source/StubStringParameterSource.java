package com.laamella.parameter_source;

import java.util.Optional;

/**
 * A string parameter source that returns the same string for all keys.
 * Suggested use is testing.
 */
public class StubStringParameterSource extends StringParameterSource {
    private String stubValue;

    public StubStringParameterSource(String stubValue) {
        this.stubValue = stubValue;
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        return Optional.ofNullable(stubValue);
    }

    public void setStubValue(String stubValue) {
        this.stubValue = stubValue;
    }
}
