package com.laamella.parameter_source;

public class ParameterSourceException extends RuntimeException {
    public ParameterSourceException(String message, Object... args) {
        super(String.format(message, args));
    }
}
