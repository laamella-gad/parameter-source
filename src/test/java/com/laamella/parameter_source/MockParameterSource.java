package com.laamella.parameter_source;

import java.util.Optional;

public class MockParameterSource extends ParameterSource {
    @Override
    public Optional<String> getOptionalString(String key) {
        return null;
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        return null;
    }
}
