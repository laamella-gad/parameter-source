package com.laamella.parameter_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

/**
 * Wraps a Java Properties object to give it the ParameterSource interface.
 * Can handle loading properties if required.
 */
public class PropertiesParameterSource implements ParameterSource {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesParameterSource.class);

    private final Properties properties;
    private final String name;

    /**
     * Use an existing Properties object.
     */
    public PropertiesParameterSource(Properties properties) {
        requireNonNull(properties);
        this.properties = properties;
        this.name = "Properties file parameter source";

        logger.info("Creating a {}.", toString());
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
        this.name = String.format("Properties file parameter source for %s:%s", resourceRelativeClass.getName(), resourceName);
        
        logger.info("Creating a {}.", toString());
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
        return log(key, Optional.ofNullable(properties.getProperty(key)));
    }

    @Override
    public String getPathSeparator() {
        return ".";
    }

    @Override
    public String toString() {
        return name;
    }
}
