package com.laamella.parameter_source;

public class MockParameterSource extends ParameterSource {
    public Object value;

    @Override
    protected <T> T getRawParameter(String key) {
        return (T) value;
    }
}
