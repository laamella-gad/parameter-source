package com.laamella.parameter_source;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

/**
 * Wraps a Java Properties object to give it the ParameterSource interface.
 * Can handle loading properties if required.
 */
public class PropertiesParameterSource implements StringParameterSource {
    private final Properties properties;

    /**
     * Use an existing Properties object.
     */
    public PropertiesParameterSource(Properties properties) {
        requireNonNull(properties);
        this.properties = properties;
    }

    /**
     * Load properties from a properties resource file called "resourceName" relative to "resourceRelativeClass"
     */
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

    /**
     * Loads properties from a file called "resourceName" found on the classpath.
     */
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

    /**
     * Creates a parameter source that prepends "keyPart" to every key requested.
     * This can be chained to go deeper and deeper.
     * Note that a "." between "keyPart" and requested keys is enforced.
     */
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
