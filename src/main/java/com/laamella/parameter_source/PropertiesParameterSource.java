package com.laamella.parameter_source;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertiesParameterSource extends ParameterSource {
    private final Properties properties;

    public PropertiesParameterSource(Properties properties) {
        this.properties = properties;
    }

    public PropertiesParameterSource(Class<?> resourceRelativeClass, String resourceName) {
        final Properties properties = new Properties();
        InputStream propertiesInputStream = resourceRelativeClass.getResourceAsStream(resourceName);
        if (propertiesInputStream == null) {
            throw new ParameterSourceException("Properties file '%s' not found", resourceName);
        }
        try {
            properties.load(propertiesInputStream);
        } catch (IOException e) {
            throw new ParameterSourceException("Can't load properties file '%s'", resourceName);
        }
        this.properties = properties;
    }

    public PropertiesParameterSource(String resourceName) {
        this(PropertiesParameterSource.class, prefixSlash(resourceName));
    }

    private static String prefixSlash(String resourceName) {
        if (resourceName.startsWith("/")) {
            return resourceName;
        }
        return "/" + resourceName;
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        return Optional.ofNullable(properties.getProperty(key));
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        final Optional<String> str = getOptionalString(key);
        try {
            return str.map(Integer::parseInt);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not an integer.", str.get(), key);
        }
    }
}
