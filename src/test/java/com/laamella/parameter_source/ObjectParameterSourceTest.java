package com.laamella.parameter_source;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ObjectParameterSourceTest {
    @Test
    public void whenRequestingAStringAndValueIsAStringThenItIsReturned() {
        StubObjectParameterSource source = new StubObjectParameterSource("abc");

        String value = source.getString("");

        assertEquals("abc", value);
    }

    @Test
    public void whenRequestingAnIntegerAndValueIsAnIntegerThenItIsReturned() {
        StubObjectParameterSource source = new StubObjectParameterSource(15);

        int value = source.getInteger("");

        assertEquals(15, value);
    }

    @Test
    public void whenRequestingALongAndValueIsALongThenItIsReturned() {
        StubObjectParameterSource source = new StubObjectParameterSource(15L);

        long value = source.getLong("");

        assertEquals(15L, value);
    }

    @Test
    public void whenRequestingAnObjectThenItIsReturned() {
        HashMap<Object, Object> stubValue = new HashMap<>();
        StubObjectParameterSource source = new StubObjectParameterSource(stubValue);

        Object value = source.getObject("");

        assertEquals(stubValue, value);
    }

}