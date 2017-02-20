package com.laamella.parameter_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * An object parameter source that returns the same object for all keys.
 * Suggested use is testing.
 */
public class StubParameterSource implements ParameterSource {
    private static final Logger logger = LoggerFactory.getLogger(StubParameterSource.class);

    private Object stubValue;

    public StubParameterSource(Object stubValue) {
        this.stubValue = stubValue;
        logger.info("Creating a {}", toString());
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        return log(key, Optional.ofNullable(stubValue));
    }

    public void setStubValue(Object stubValue) {
        logger.debug("Changing stub value to {}", stubValue);
        this.stubValue = stubValue;
    }

    @Override
    public String toString() {
        return String.format("stub parameter source with stub value '%s'", stubValue);
    }
}
