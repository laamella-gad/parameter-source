package com.laamella.parameter_source;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InMemoryStringParameterSourceTest {
    @Test
    public void whenAValueIsPutInThenItIsAvailable() {
        InMemoryStringParameterSource src = new InMemoryStringParameterSource()
                .put("abc", "def");
        Object abc = src.getString("abc");

        assertEquals("def", abc);
    }

    @Test(expected = ParameterSourceException.class)
    public void whenAValueIsNotPutInThenItIsNotAvailable() {
        InMemoryStringParameterSource src = new InMemoryStringParameterSource();
        src.getString("abc");
    }

}
