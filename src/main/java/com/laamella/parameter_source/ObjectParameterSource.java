package com.laamella.parameter_source;

import java.util.Optional;

/**
 * A parameter source that stores objects.
 * If a retrieve value is not of the requested type,
 * conversion will be attempted.
 */
public interface ObjectParameterSource extends ParameterSource {
    default Optional<String> getOptionalString(String key) {
        return getOptionalObject(key).map(Object::toString);
    }

    default Optional<Integer> getOptionalInteger(String key) {
        Optional<Object> optionalObject = getOptionalObject(key);
        if (optionalObject.isPresent()) {
            Object o = optionalObject.get();
            if (o instanceof Integer) {
                return Optional.of((Integer) o);
            }
            throw new ParameterSourceException("%s does not contain an integer value.", key);
        } else {
            return Optional.empty();
        }
    }

    default Optional<Long> getOptionalLong(String key) {
        Optional<Object> optionalObject = getOptionalObject(key);
        if (optionalObject.isPresent()) {
            Object o = optionalObject.get();
            if (o instanceof Long) {
                return Optional.of((Long) o);
            }
            throw new ParameterSourceException("%s does not contain a long value.", key);
        } else {
            return Optional.empty();
        }
    }

    @Override
    default Optional<Float> getOptionalFloat(String key) {
        Optional<Object> optionalObject = getOptionalObject(key);
        if (optionalObject.isPresent()) {
            Object o = optionalObject.get();
            if (o instanceof Float) {
                return Optional.of((Float) o);
            }
            throw new ParameterSourceException("%s does not contain a float value.", key);
        } else {
            return Optional.empty();
        }
    }

    @Override
    default Optional<Double> getOptionalDouble(String key) {
        Optional<Object> optionalObject = getOptionalObject(key);
        if (optionalObject.isPresent()) {
            Object o = optionalObject.get();
            if (o instanceof Double) {
                return Optional.of((Double) o);
            }
            throw new ParameterSourceException("%s does not contain a double value.", key);
        } else {
            return Optional.empty();
        }
    }
}
