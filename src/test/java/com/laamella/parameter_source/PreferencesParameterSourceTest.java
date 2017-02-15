package com.laamella.parameter_source;

import org.junit.Test;

import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static org.junit.Assert.*;

public class PreferencesParameterSourceTest {
    @Test
    public void whenAValueExistsItIsReturned() throws BackingStoreException {
        Preferences.userRoot().clear();
        Preferences.userRoot().put("abc", "def");

        PreferencesParameterSource source = new PreferencesParameterSource();

        String value = source.getString("abc");

        assertEquals("def", value);
    }

    @Test
    public void whenAValueDoesNotExistItIsEmpty() throws BackingStoreException {
        Preferences.userRoot().clear();

        PreferencesParameterSource source = new PreferencesParameterSource();

        Optional<String> value = source.getOptionalString("qix");

        assertEquals(false, value.isPresent());
    }
}
