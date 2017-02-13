package com.laamella.parameter_source;

public class ParameterSourceException extends RuntimeException {
    ParameterSourceException(String message, Object... args) {
        super(String.format(message, args));
    }

    public ParameterSourceException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }
}
