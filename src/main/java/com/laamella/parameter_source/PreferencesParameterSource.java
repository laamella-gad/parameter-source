package com.laamella.parameter_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.prefs.Preferences;

import static java.util.Objects.requireNonNull;

/**
 * Uses a Java Preferences object as the parameter source.
 * Note that due to the implementation of Java Preferences,
 * errors in the backing store will go unnoticed and the source will return only "Optional.empty()"
 */
public class PreferencesParameterSource implements ParameterSource {
    private static final Logger logger = LoggerFactory.getLogger(PreferencesParameterSource.class);

    private final Preferences preferences;

    /**
     * Gives access to the user root.
     */
    public PreferencesParameterSource() {
        this(Preferences.userRoot());
        logger.info("Creating a {}.", toString());
    }

    public PreferencesParameterSource(Preferences preferences) {
        requireNonNull(preferences);
        this.preferences = preferences;
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        return log(key, Optional.ofNullable(preferences.get(key, null)));
    }

    @Override
    public String toString() {
        return "Java Preferences parameter source";
    }
}
