package com.laamella.parameter_source;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

    protected int stringToInteger(String key, String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not an integer.", str, key);
        }
    }

    @Override
    public Optional<Long> getOptionalLong(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToLong(key, s));
    }

    @Override
    public Optional<List<String>> getOptionalStringList(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToList(s, i -> i));
    }

    protected <T> List<T> stringToList(String input, Function<String, T> itemConverter) {
        final List<T> result = new ArrayList<>();
        for (String itemString : input.split(",")) {
            result.add(itemConverter.apply(itemString.trim()));
        }
        return result;
    }

    protected long stringToLong(String key, String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a long.", str, key);
        }
    }

    @Override
    public Optional<Float> getOptionalFloat(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToFloat(key, s));
    }

    protected float stringToFloat(String key, String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a float.", str, key);
        }
    }

    @Override
    public Optional<Double> getOptionalDouble(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToDouble(key, s));
    }

    protected double stringToDouble(String key, String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a double.", str, key);
        }
    }

    @Override
    public Optional<Boolean> getOptionalBoolean(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToBoolean(key, s));
    }

    protected boolean stringToBoolean(String key, String str) {
        switch (str.toLowerCase()) {
            case "true":
            case "t":
            case "y":
            case "yes":
            case "1":
            case "enable":
            case "enabled":
                return true;
            case "false":
            case "f":
            case "n":
            case "no":
            case "0":
            case "disable":
            case "disabled":
                return false;
            default:
                throw new ParameterSourceException("Value %s of %s is not a boolean.", str, key);
        }
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        requireNonNull(key);

        return getOptionalString(key).map(s -> stringToObject(key, s));
    }

    protected Object stringToObject(String key, String str) {
        return str;
    }

}
