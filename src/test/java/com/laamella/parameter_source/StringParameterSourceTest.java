package com.laamella.parameter_source;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class StringParameterSourceTest {
    @Test
    public void whenRequestingAnIntegerThenANumericValueIsConverted() {
        int value = new StubStringParameterSource("123").getInteger("");

        assertEquals(123, value);
    }

    @Test
    public void whenRequestingALongThenANumericValueIsConverted() {
        long value = new StubStringParameterSource("123123").getLong("");

        assertEquals(123123, value);
    }

    @Test
    public void whenRequestingADoubleThenANumericValueIsConverted() {
        double value = new StubStringParameterSource("123.123").getDouble("");

        assertEquals(123.123, value, 0);
    }

    @Test
    public void whenRequestingAFloatThenANumericValueIsConverted() {
        float value = new StubStringParameterSource("123.123").getFloat("");

        assertEquals(123.123f, value, 0);
    }

    @Test
    public void whenRequestingAStringListThenAStringIsSplitOnCommas() {
        List<String> list = new StubStringParameterSource("henk, piet, klaas").getStringList("");

        assertEquals(3, list.size());
        assertEquals("henk", list.get(0));
        assertEquals("piet", list.get(1));
        assertEquals("klaas", list.get(2));
    }

    @Test(expected = ParameterSourceException.class)
    public void whenRequestingAnIntegerThenABadValueFails() {
        new StubStringParameterSource("qqqqq").getInteger("");
    }

    @Test
    public void whenRequestingAnObjectThenItOnlyReturnsStrings() {
        StubStringParameterSource source = new StubStringParameterSource("qqqqq");

        assertEquals("qqqqq", source.getObject(""));
    }
}
