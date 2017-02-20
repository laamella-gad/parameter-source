package com.laamella.parameter_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * This source has no external storage.
 * It only contains key-value pairs that have been put in using the "put" method.
 */
public class InMemoryParameterSource implements ParameterSource {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryParameterSource.class);

    private final Map<String, Object> storage = new HashMap<>();
    
    public InMemoryParameterSource(){
        logger.info("Creating an {}.", toString());
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        requireNonNull(key);

        return log(key, Optional.ofNullable(storage.get(key)));
    }

    public InMemoryParameterSource put(String key, Object o) {
        requireNonNull(key);
        storage.put(key, o);
        logger.debug("Stored `{}` -> `{}` in memory", key, o);
        return this;
    }

    @Override
    public String toString() {
        return "in memory parameter source";
    }
}
