package com.laamella.parameter_source;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * A parameter source that stores strings.
 * Getting types that are not strings will attempt parsing the string value into that type.
 */
public interface StringParameterSource extends ParameterSource {
    @Override
    default Optional<Integer> getOptionalInteger(String key) {
        requireNonNull(key);

        final Optional<String> str = getOptionalString(key);
        try {
            return str.map(Integer::parseInt);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not an integer.", str.get(), key);
        }
    }

    @Override
    default Optional<Long> getOptionalLong(String key) {
        requireNonNull(key);

        final Optional<String> str = getOptionalString(key);
        try {
            return str.map(Long::parseLong);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a long.", str.get(), key);
        }
    }

    @Override
    default Optional<Float> getOptionalFloat(String key) {
        requireNonNull(key);

        final Optional<String> str = getOptionalString(key);
        try {
            return str.map(Float::parseFloat);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a float.", str.get(), key);
        }
    }

    @Override
    default Optional<Double> getOptionalDouble(String key) {
        requireNonNull(key);

        final Optional<String> str = getOptionalString(key);
        try {
            return str.map(Double::parseDouble);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a double.", str.get(), key);
        }
    }

    @Override
    default Optional<Object> getOptionalObject(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> (Object) s);
    }
}
