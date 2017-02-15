package com.laamella.parameter_source;

import java.util.List;
import java.util.Optional;

/**
 * A parameter source that stores objects.
 * If a retrieve value is not of the requested type,
 * conversion will be attempted.
 */
public abstract class ObjectParameterSource implements ParameterSource {
    @Override
    public Optional<String> getOptionalString(String key) {
        return getOptionalObject(key).map(o -> objectToString(key, o));
    }

    public static String objectToString(String key, Object o) {
        return o.toString();
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        return getOptionalObject(key).map(o -> objectToInteger(key, o));
    }

    public static Integer objectToInteger(String key, Object o) {
        if (o instanceof Integer) {
            return (Integer) o;
        }
        throw new ParameterSourceException("%s does not contain an integer value.", key);
    }

    @Override
    public Optional<List<String>> getOptionalStringList(String key) {
        return getOptionalObject(key).map(o -> objectToList(key, o, String.class));
    }

    public static <T> List<T> objectToList(String key, Object o, Class<T> type) {
        if (o instanceof List) {
            // TODO we could check if there are really objects of type "type" in the list
            return (List<T>) o;
        }
        throw new ParameterSourceException("%s does not contain a list value.", key);
    }

    @Override
    public Optional<Long> getOptionalLong(String key) {
        return getOptionalObject(key).map(o -> objectToLong(key, o));
    }

    public static Long objectToLong(String key, Object o) {
        if (o instanceof Long) {
            return (Long) o;
        }
        throw new ParameterSourceException("%s does not contain an integer value.", key);
    }

    @Override
    public Optional<Float> getOptionalFloat(String key) {
        return getOptionalObject(key).map(o -> objectToFloat(key, o));
    }

    public static Float objectToFloat(String key, Object o) {
        if (o instanceof Float) {
            return (Float) o;
        }
        throw new ParameterSourceException("%s does not contain a float value.", key);
    }


    @Override
    public Optional<Double> getOptionalDouble(String key) {
        return getOptionalObject(key).map(o -> objectToDouble(key, o));
    }

    public static Double objectToDouble(String key, Object o) {
        if (o instanceof Double) {
            return (Double) o;
        }
        throw new ParameterSourceException("%s does not contain a double value.", key);
    }


    @Override
    public Optional<Boolean> getOptionalBoolean(String key) {
        return getOptionalObject(key).map(o -> objectToBoolean(key, o));
    }

    public static Boolean objectToBoolean(String key, Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        throw new ParameterSourceException("%s does not contain a boolean value.", key);
    }

}
