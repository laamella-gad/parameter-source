package com.laamella.parameter_source;

/**
 * Abstract interface to a parameter (simple key-value) store, adds checking of required parameters, a tight interface, logging, and easy testing.
 */
public abstract class ParameterSource {
    /**
     * Get a parameter that is required.
     * @param key The name of the parameter
     * @return the value of the parameter. Never null.
     * @throws MissingParameterException if the value is null or missing.
     */
    public <T> T getParameter(String key) throws MissingParameterException {
        T value = getRawParameter(key);
        if (value == null) {
            throw new MissingParameterException(key);
        }
        return value;
    }

    /**
     * Get an optional parameter
     * @param key the name of the parameter
     * @param defaultValue the value that is used when the parameter is missing
     * @return the value of the parameter, or defaultValue when it's missing.
     */
    public <T> T getParameter(String key, T defaultValue) {
        T value = getRawParameter(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    /**
     * Get a parameter value from the actual storage. When generalizing this class to support multiple storages (database, property file...) this method would be abstract in a base class.
     */
    protected abstract <T> T getRawParameter(String key);

}
