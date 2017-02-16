package com.laamella.parameter_source;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static com.laamella.parameter_source.TypeConverter.*;

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

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        return getOptionalObject(key).map(o -> objectToInteger(key, o));
    }

    @Override
    public Optional<List<String>> getOptionalStringList(String key) {
        return getOptionalObject(key).map(o -> objectToList(key, o, String.class));
    }

    @Override
    public Optional<Long> getOptionalLong(String key) {
        return getOptionalObject(key).map(o -> objectToLong(key, o));
    }


    @Override
    public Optional<URL> getOptionalUrl(String key) {
        return getOptionalObject(key).map(o -> objectToUrl(key, o));
    }

    @Override
    public Optional<URI> getOptionalUri(String key) {
        return getOptionalObject(key).map(o -> objectToUri(key, o));
    }

    @Override
    public Optional<Float> getOptionalFloat(String key) {
        return getOptionalObject(key).map(o -> objectToFloat(key, o));
    }


    @Override
    public Optional<Double> getOptionalDouble(String key) {
        return getOptionalObject(key).map(o -> objectToDouble(key, o));
    }

    @Override
    public Optional<Boolean> getOptionalBoolean(String key) {
        return getOptionalObject(key).map(o -> objectToBoolean(key, o));
    }
}
