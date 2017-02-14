package com.laamella.parameter_source;

import java.util.Optional;

public class StubStringParameterSource implements StringParameterSource {
    private final String stubValue;

    public StubStringParameterSource(String stubValue) {
        this.stubValue = stubValue;
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        return Optional.ofNullable(stubValue);
    }
}
