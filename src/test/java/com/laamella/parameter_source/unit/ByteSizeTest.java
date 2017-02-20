package com.laamella.parameter_source.unit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ByteSizeTest {
    @Test
    public void bytes() {
        assertEquals(100, ByteSize.ofBytes(100).getBytes());
    }

    @Test
    public void kilobytes() {
        assertEquals(102400, ByteSize.ofKilobytes(100).getBytes());
    }

    @Test
    public void petabytes() {
        assertEquals(1125899906842624L, ByteSize.ofPetabytes(1).getBytes());
        assertEquals(1024, ByteSize.ofPetabytes(1).getTerabytes());
    }

    @Test
    public void parsing() {
        assertEquals(0L, ByteSize.parse("key", "0").getBytes());
        assertEquals(10L, ByteSize.parse("key", "10").getBytes());
        assertEquals(10L, ByteSize.parse("key", "10 bytes").getBytes());
        assertEquals(10L, ByteSize.parse("key", "10b").getBytes());
        assertEquals(10240L, ByteSize.parse("key", "10kb").getBytes());
        assertEquals(1024L * 1024 * 10, ByteSize.parse("key", "10mb").getBytes());
        assertEquals(1024L * 1024 * 1024, ByteSize.parse("key", "1gb").getBytes());
        assertEquals(1024L * 1024 * 1024 * 1024, ByteSize.parse("key", "1tb").getBytes());
        assertEquals(1024L * 1024 * 1024 * 1024 * 1024, ByteSize.parse("key", "1pb").getBytes());
        assertEquals(1024L * 1024 * 1024 * 1024 * 1024 * 1024, ByteSize.parse("key", "1EB").getBytes());
        assertEquals(1024L * 1024 * 1024 * 1024 * 1024 * 1024 + 1, ByteSize.parse("key", "1 exabyte 1").getBytes());
    }

    @Test
    public void toStringing() {
        assertEquals("0B", ByteSize.parse("key", "0").toString());
        assertEquals("10B", ByteSize.parse("key", "10").toString());
        assertEquals("10B", ByteSize.parse("key", "10 bytes").toString());
        assertEquals("10B", ByteSize.parse("key", "10b").toString());
        assertEquals("10KB", ByteSize.parse("key", "10kb").toString());
        assertEquals("10MB", ByteSize.parse("key", "10mb").toString());
        assertEquals("1GB", ByteSize.parse("key", "1gb").toString());
        assertEquals("1TB", ByteSize.parse("key", "1tb").toString());
        assertEquals("1PB", ByteSize.parse("key", "1pb").toString());
        assertEquals("1EB", ByteSize.parse("key", "1eb").toString());
        assertEquals("1EB 1B", ByteSize.parse("key", "1 exabyte 1").toString());
    }
}
