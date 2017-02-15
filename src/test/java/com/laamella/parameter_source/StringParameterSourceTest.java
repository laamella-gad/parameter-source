package com.laamella.parameter_source;

import org.junit.Test;

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
