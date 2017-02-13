package com.laamella.parameter_source;

import java.util.Optional;

public abstract class StringParameterSource extends ParameterSource {
    @Override
    public abstract Optional<String> getOptionalString(String key);

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        final Optional<String> str = getOptionalString(key);
        try {
            return str.map(Integer::parseInt);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not an integer.", str.get(), key);
        }
    }
}
