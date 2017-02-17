package com.laamella.parameter_source;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InMemoryParameterSourceTest {
    @Test
    public void whenAValueIsPutInThenItIsAvailable() {
        Object o = new Object();
        InMemoryParameterSource src = new InMemoryParameterSource()
                .put("abc", o);
        Object abc = src.getObject("abc");

        assertEquals(o, abc);
    }

    @Test(expected = ParameterSourceException.class)
    public void whenAValueIsNotPutInThenItIsNotAvailable() {
        InMemoryParameterSource src = new InMemoryParameterSource();
        src.getObject("abc");
    }
}
