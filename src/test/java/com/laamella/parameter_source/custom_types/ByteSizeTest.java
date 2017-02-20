package com.laamella.parameter_source.custom_types;

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
        assertEquals(0L, ByteSize.parse("0").get().getBytes());
        assertEquals(10L, ByteSize.parse("10").get().getBytes());
        assertEquals(10L, ByteSize.parse("10 bytes").get().getBytes());
        assertEquals(10L, ByteSize.parse("10b").get().getBytes());
        assertEquals(10240L, ByteSize.parse("10kb").get().getBytes());
        assertEquals(1024L * 1024 * 10, ByteSize.parse("10mb").get().getBytes());
        assertEquals(1024L * 1024 * 1024, ByteSize.parse("1gb").get().getBytes());
        assertEquals(1024L * 1024 * 1024 * 1024, ByteSize.parse("1tb").get().getBytes());
        assertEquals(1024L * 1024 * 1024 * 1024 * 1024, ByteSize.parse("1pb").get().getBytes());
        assertEquals(1024L * 1024 * 1024 * 1024 * 1024 * 1024, ByteSize.parse("1EB").get().getBytes());
        assertEquals(1024L * 1024 * 1024 * 1024 * 1024 * 1024 + 1, ByteSize.parse("1 exabyte 1").get().getBytes());
    }

    @Test
    public void toStringing() {
        assertEquals("0B", ByteSize.parse("0").get().toString());
        assertEquals("10B", ByteSize.parse("10").get().toString());
        assertEquals("10B", ByteSize.parse("10 bytes").get().toString());
        assertEquals("10B", ByteSize.parse("10b").get().toString());
        assertEquals("10KB", ByteSize.parse("10kb").get().toString());
        assertEquals("10MB", ByteSize.parse("10mb").get().toString());
        assertEquals("1GB", ByteSize.parse("1gb").get().toString());
        assertEquals("1TB", ByteSize.parse("1tb").get().toString());
        assertEquals("1PB", ByteSize.parse("1pb").get().toString());
        assertEquals("1EB", ByteSize.parse("1eb").get().toString());
        assertEquals("1EB 1B", ByteSize.parse("1 exabyte 1").get().toString());
    }
}
