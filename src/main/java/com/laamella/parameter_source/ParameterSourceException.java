package com.laamella.parameter_source;

import java.util.function.Supplier;

public class ParameterSourceException extends RuntimeException {
    public ParameterSourceException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ParameterSourceException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }

    public static Supplier<ParameterSourceException> missingKeyException(String key) {
        return () -> new ParameterSourceException("Key %s is missing.", key);
    }

    public static Supplier<ParameterSourceException> badValueException(String key, String value, String description) {
        return () -> new ParameterSourceException("Value %s for %s is not a %s", value, key, description);
    }
}
