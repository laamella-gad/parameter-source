package com.laamella.parameter_source;

import java.util.Optional;

public class MockParameterSource extends StringParameterSource {
    public Optional<String> value;

    @Override
    public Optional<String> getOptionalString(String key) {
        return value;
    }
}
