package com.laamella.parameter_source;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ObjectParameterSourceTest {
    @Test
    public void whenRequestingAnObjectThenItIsReturned() {
        HashMap<Object, Object> stubValue = new HashMap<>();
        StubObjectParameterSource source = new StubObjectParameterSource(stubValue);

        Object value = source.getObject("");

        assertEquals(stubValue, value);
    }

}
