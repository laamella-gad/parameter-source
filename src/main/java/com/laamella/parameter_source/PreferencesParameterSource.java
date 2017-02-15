package com.laamella.parameter_source;

import java.util.Optional;
import java.util.prefs.Preferences;

import static java.util.Objects.requireNonNull;

/**
 * Uses a Java Preferences object as the parameter source.
 * Note that due to the implementation of Java Preferences,
 * errors in the backing store will go unnoticed and the source will return only "Optional.empty()"
 */
public class PreferencesParameterSource implements StringParameterSource {
    private final Preferences preferences;

    /**
     * Gives access to the user root.
     */
    public PreferencesParameterSource() {
        this(Preferences.userRoot());
    }

    public PreferencesParameterSource(Preferences preferences) {
        requireNonNull(preferences);
        this.preferences = preferences;
    }

    @Override
    public Optional<String> getOptionalString(String key) {
        return Optional.ofNullable(preferences.get(key, null));
    }
}
