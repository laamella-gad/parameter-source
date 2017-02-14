package com.laamella.parameter_source;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InMemoryObjectParameterSourceTest {
    @Test
    public void whenAValueIsPutInThenItIsAvailable() {
        Object o = new Object();
        InMemoryObjectParameterSource src = new InMemoryObjectParameterSource()
                .put("abc", o);
        Object abc = src.getObject("abc", Object.class);

        assertEquals(o, abc);
    }

    @Test(expected = ParameterSourceException.class)
    public void whenAValueIsNotPutInThenItIsNotAvailable() {
        InMemoryObjectParameterSource src = new InMemoryObjectParameterSource();
        src.getObject("abc", Object.class);
    }
}