package com.laamella.parameter_source;

import java.util.List;
import java.util.Optional;

import static com.laamella.parameter_source.TypeConverter.*;
import static java.util.Objects.requireNonNull;

/**
 * A parameter source that stores strings.
 * Getting types that are not strings will attempt parsing the string value into that type.
 * Getting lists will split the value on commas,
 * then attempt to convert each string between the commas to the requested type.
 */
public abstract class StringParameterSource implements ParameterSource {
    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToInteger(key, s));
    }

    @Override
    public Optional<Long> getOptionalLong(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToLong(key, s));
    }

    @Override
    public Optional<List<String>> getOptionalStringList(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToList(key, s, i -> i));
    }

    @Override
    public Optional<Float> getOptionalFloat(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToFloat(key, s));
    }

    @Override
    public Optional<Double> getOptionalDouble(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToDouble(key, s));
    }

    @Override
    public Optional<Boolean> getOptionalBoolean(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToBoolean(key, s));
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToObject(key, s));
    }
}
