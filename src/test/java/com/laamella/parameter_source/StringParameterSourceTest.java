package com.laamella.parameter_source;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringParameterSourceTest {
    @Test
    public void whenRequestingAnIntegerThenANumericValueIsConverted() {
        int optionalInteger = new StubStringParameterSource("123").getInteger("");

        assertEquals(123, optionalInteger);
    }

    @Test
    public void whenRequestingAnIntegerThenAHexValueIsConverted() {
        int optionalInteger = new StubStringParameterSource("0xCAFE").getInteger("");

        assertEquals(0xcafe, optionalInteger);
    }

    @Test(expected = ParameterSourceException.class)
    public void whenRequestingAnIntegerThenABadValueFails() {
        new StubStringParameterSource("qqqqq").getInteger("");
    }
}