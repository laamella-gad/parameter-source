package com.laamella.parameter_source;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertiesParameterSource implements StringParameterSource {
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
            throw new ParameterSourceException(e, "Can't load properties file '%s'", resourceName);
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
}
