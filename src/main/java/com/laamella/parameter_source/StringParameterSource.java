package com.laamella.parameter_source;

import java.util.Optional;

public interface StringParameterSource extends ParameterSource {
    @Override
    default Optional<Integer> getOptionalInteger(String key) {
        final Optional<String> str = getOptionalString(key);
        try {
            return str.map(Integer::parseInt);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not an integer.", str.get(), key);
        }
    }
}
