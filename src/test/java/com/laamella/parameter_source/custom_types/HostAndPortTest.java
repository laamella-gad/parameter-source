package com.laamella.parameter_source.custom_types;

import com.laamella.parameter_source.HostAndPort;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HostAndPortTest {
    @Test
    public void parseIpPort() {
        HostAndPort hostAndPort = HostAndPort.parse("192.168.1.1:8080").get();
        assertEquals("192.168.1.1", hostAndPort.getHost());
        assertEquals(new Integer(8080), hostAndPort.getPort().get());
    }

    @Test
    public void parseIpNoPort() {
        HostAndPort hostAndPort = HostAndPort.parse("192.168.1.1").get();
        assertEquals("192.168.1.1", hostAndPort.getHost());
        assertEquals(false, hostAndPort.getPort().isPresent());
    }

    @Test
    public void parseHostPort() {
        HostAndPort hostAndPort = HostAndPort.parse("bla.com:1234").get();
        assertEquals("bla.com", hostAndPort.getHost());
        assertEquals(new Integer(1234), hostAndPort.getPort().get());
    }

    @Test
    public void parseHostNoPort() {
        HostAndPort hostAndPort = HostAndPort.parse("bla.com").get();
        assertEquals("bla.com", hostAndPort.getHost());
        assertEquals(false, hostAndPort.getPort().isPresent());
    }

    @Test
    public void parseBadPort() {
        assertEquals(false, HostAndPort.parse("192.168.1.1:xxx").isPresent());
    }
}
