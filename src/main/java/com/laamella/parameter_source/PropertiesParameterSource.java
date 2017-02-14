package com.laamella.parameter_source;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

public class PropertiesParameterSource implements StringParameterSource {
    private final Properties properties;

    public PropertiesParameterSource(Properties properties) {
        requireNonNull(properties);
        this.properties = properties;
    }

    public PropertiesParameterSource(Class<?> resourceRelativeClass, String resourceName) {
        requireNonNull(resourceRelativeClass);
        requireNonNull(resourceName);

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
        requireNonNull(resourceName);
        if (resourceName.startsWith("/")) {
            return resourceName;
        }
        return "/" + resourceName;
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        requireNonNull(key);
        return Optional.ofNullable(properties.getProperty(key));
    }

    public SubParameterSource subSource(String keyPart) {
        requireNonNull(keyPart);
        return new SubParameterSource(this, keyPart, (a, b) -> {
            while (!a.isEmpty() && a.endsWith(".")) {
                a = a.substring(0, a.length() - 1);
            }
            while (!b.isEmpty() && b.startsWith(".")) {
                b = b.substring(1);
            }
            if (a.isEmpty()) {
                return b;
            }
            if (b.isEmpty()) {
                return a;
            }
            return a + "." + b;
        });
    }
}
