package com.laamella.parameter_source;

import java.util.Optional;
import java.util.function.BiFunction;

import static java.util.Objects.requireNonNull;

/**
 * Supports the "subSource" method.
 */
public class SubParameterSource implements ParameterSource {
    private final ParameterSource delegate;
    private final String keyPart;
    private final BiFunction<String, String, String> keyCombiner;

    public SubParameterSource(ParameterSource delegate, String keyPart, BiFunction<String, String, String> keyCombiner) {
        requireNonNull(delegate);
        requireNonNull(keyPart);
        requireNonNull(keyCombiner);

        this.delegate = delegate;
        this.keyPart = keyPart;
        this.keyCombiner = keyCombiner;
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

    public ParameterSource subSource(String keyPart) {
        requireNonNull(keyPart);
        return new SubParameterSource(this, keyPart, keyCombiner);
    }

    public String combineKeys(String first, String second) {
        requireNonNull(first);
        requireNonNull(second);
        
        return keyCombiner.apply(first, second);
    }
}
