package com.laamella.parameter_source;

public class MissingParameterException extends Throwable {
    public MissingParameterException(String key) {
        super("There is no value for key " + key);
    }
}
