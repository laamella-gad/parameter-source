package com.laamella.parameter_source;

import java.util.Optional;
import java.util.prefs.Preferences;

import static java.util.Objects.requireNonNull;

/**
 * Uses a Java Preferences object as the parameter source.
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
