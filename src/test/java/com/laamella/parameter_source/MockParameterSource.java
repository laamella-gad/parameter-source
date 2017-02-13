package com.laamella.parameter_source;

import java.util.Optional;

public class MockParameterSource extends ParameterSource<String> {
    public Optional<String> value;

    protected Optional<String> getOptionalValueFromSource(String key) {
        return value;
    }
}
