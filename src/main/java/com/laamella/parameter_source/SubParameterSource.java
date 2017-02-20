package com.laamella.parameter_source;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Supports the "subSource" method.
 */
public class SubParameterSource implements ParameterSource {
    private final ParameterSource delegate;
    private final String keyPart;
    private final String pathSeparator;

    SubParameterSource(ParameterSource delegate, String keyPart, String pathSeparator) {
        requireNonNull(delegate);
        requireNonNull(keyPart);
        requireNonNull(pathSeparator);

        this.delegate = delegate;
        this.keyPart = keyPart;
        this.pathSeparator = pathSeparator;
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        requireNonNull(key);
        return delegate.getOptionalString(combineKeys(keyPart, key));
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        requireNonNull(key);
        return delegate.getOptionalInteger(combineKeys(keyPart, key));
    }

    @Override
    public Optional<Long> getOptionalLong(String key) {
        requireNonNull(key);
        return delegate.getOptionalLong(combineKeys(keyPart, key));
    }

    @Override
    public Optional<Float> getOptionalFloat(String key) {
        requireNonNull(key);
        return delegate.getOptionalFloat(combineKeys(keyPart, key));
    }

    @Override
    public Optional<List<String>> getOptionalStringList(String key) {
        requireNonNull(key);
        return delegate.getOptionalStringList(combineKeys(keyPart, key));
    }

    @Override
    public Optional<Double> getOptionalDouble(String key) {
        requireNonNull(key);
        return delegate.getOptionalDouble(combineKeys(keyPart, key));
    }

    @Override
    public Optional<Boolean> getOptionalBoolean(String key) {
        requireNonNull(key);
        return delegate.getOptionalBoolean(combineKeys(keyPart, key));
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        requireNonNull(key);
        return delegate.getOptionalObject(combineKeys(keyPart, key));
    }

    @Override
    public Optional<Class<?>> getOptionalClass(String key) {
        requireNonNull(key);
        return delegate.getOptionalClass(combineKeys(keyPart, key));
    }

    @Override
    public Optional<URL> getOptionalUrl(String key) {
        requireNonNull(key);
        return delegate.getOptionalUrl(combineKeys(keyPart, key));
    }

    @Override
    public Optional<URI> getOptionalUri(String key) {
        requireNonNull(key);
        return delegate.getOptionalUri(combineKeys(keyPart, key));
    }

    @Override
    public <T extends Enum<?>> Optional<T> getOptionalEnum(String key, Class<T> enumType) {
        requireNonNull(key);
        return delegate.getOptionalEnum(combineKeys(keyPart, key), enumType);
    }

    @Override
    public SubParameterSource subSource(String keyPart) {
        requireNonNull(keyPart);
        return new SubParameterSource(this, keyPart, getPathSeparator());
    }

    @Override
    public String getPathSeparator() {
        return pathSeparator;
    }

    private String combineKeys(String a, String b) {
        requireNonNull(a);
        requireNonNull(b);

        while (!a.isEmpty() && a.endsWith(getPathSeparator())) {
            a = a.substring(0, a.length() - 1);
        }
        while (!b.isEmpty() && b.startsWith(getPathSeparator())) {
            b = b.substring(1);
        }
        if (a.isEmpty()) {
            return b;
        }
        if (b.isEmpty()) {
            return a;
        }
        return a + getPathSeparator() + b;
    }

    @Override
    public String toString() {
        return String.format("sub parameter source with key part %s for %s", keyPart, delegate);
    }
}
