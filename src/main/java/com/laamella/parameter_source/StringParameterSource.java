package com.laamella.parameter_source;

import java.net.URI;
import java.net.URL;
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
    public <T extends Enum<?>> Optional<T> getOptionalEnum(String key, Class<T> enumType) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToEnum(key, s, enumType));
    }

    @Override
    public Optional<Class<?>> getOptionalClass(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToClass(key, s));
    }

    @Override
    public Optional<Long> getOptionalLong(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToLong(key, s));
    }

    @Override
    public Optional<URL> getOptionalUrl(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToUrl(key, s));

    }

    @Override
    public Optional<URI> getOptionalUri(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToUri(key, s));

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
