package com.laamella.parameter_source;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    public void whenRequestingADoubleAndValueIsADoubleThenItIsReturned() {
        StubObjectParameterSource source = new StubObjectParameterSource(15.0);

        double value = source.getDouble("");

        assertEquals(15.0, value, 0);
    }

    @Test
    public void whenRequestingAFloatAndValueIsAFloatThenItIsReturned() {
        StubObjectParameterSource source = new StubObjectParameterSource(15f);

        float value = source.getFloat("");

        assertEquals(15f, value, 0);
    }

    @Test
    public void whenRequestingABooleanAndValueIsABooleanThenItIsReturned() {
        StubObjectParameterSource source = new StubObjectParameterSource(false);

        boolean value = source.getBoolean("");

        assertEquals(false, value);
    }

    @Test
    public void whenRequestingAStringListAndValueIsAStringListThenItIsReturned() {
        List<String> list = Arrays.asList("a", "b");
        StubObjectParameterSource source = new StubObjectParameterSource(list);

        List<String> value = source.getStringList("");

        assertEquals(list, value);
    }

    @Test
    public void whenRequestingAnObjectThenItIsReturned() {
        HashMap<Object, Object> stubValue = new HashMap<>();
        StubObjectParameterSource source = new StubObjectParameterSource(stubValue);

        Object value = source.getObject("");

        assertEquals(stubValue, value);
    }

}