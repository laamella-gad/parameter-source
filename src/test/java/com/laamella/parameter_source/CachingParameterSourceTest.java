package com.laamella.parameter_source;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CachingParameterSourceTest {
    @Test
    public void cachingWorks() {
        StubParameterSource stubSource = new StubParameterSource("one");
        CachingParameterSource cachingSource = new CachingParameterSource(stubSource);

        String a1 = cachingSource.getString("a");
        stubSource.setStubValue("two");
        String a2 = cachingSource.getString("a");
        cachingSource.flush();
        stubSource.setStubValue("three");
        String c = cachingSource.getString("a");

        assertEquals("one", a1);
        // "one" was cached
        assertEquals("one", a2);
        // until we flushed
        assertEquals("three", c);
    }

}
