package com.laamella.parameter_source;

import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static com.laamella.parameter_source.TypeConverter.*;
import static org.junit.Assert.assertEquals;

public class TypeConverterTest {
    @Test
    public void durationsInPseudoIso8601() {
        assertEquals("PT0.001S", parseDuration("", "1ms").toString());
        assertEquals("PT0.000000001S", parseDuration("", "1ns").toString());
        assertEquals("PT120H1S", parseDuration("", "5D 1S").toString());
        assertEquals("PT51H4M5.006000007S", parseDuration("", "2d 3h 4m 5s 6ms 7ns").toString());
        assertEquals("PT51H4M5.006000007S", parseDuration("", "2 d 3 h 4 m 5 s 6 ms 7 ns").toString());
        assertEquals("PT51H4M5.006000007S", parseDuration("", "2 days 3 hours 4 minutes 5 seconds 6 milliseconds 7 nanoseconds").toString());
        assertEquals("PT25H1M1.001000001S", parseDuration("", "1 day 1 hour 1 minute 1 second 1 millisecond 1 nanosecond").toString());
    }

    @Test
    public void durationsWithSemicolons() {
        assertEquals("PT10S", parseDuration("", ":10").toString());
        assertEquals("PT10M10S", parseDuration("", "10:10").toString());
        assertEquals("PT14H12M10S", parseDuration("", "14:12:10").toString());
        assertEquals("PT14H12M10.123S", parseDuration("", "14:12:10.123").toString());
        assertEquals("PT0S", parseDuration("", "0").toString());
        assertEquals("PT12.345S", parseDuration("", "12.345").toString());
    }

    @Test
    public void whenRequestingAStringAndValueIsAStringThenItIsReturned() {
        assertEquals("abc", objectToString("key", "abc"));
    }

    @Test
    public void whenRequestingAnIntegerAndValueIsAnIntegerThenItIsReturned() {
        assertEquals(15, (int) objectToInteger("key", 15));
    }

    @Test
    public void whenRequestingALongAndValueIsALongThenItIsReturned() {
        assertEquals(15L, (long) objectToLong("key", 15L));
    }

    @Test
    public void whenRequestingADoubleAndValueIsADoubleThenItIsReturned() {
        assertEquals(15.0, objectToDouble("key", 15.0), 0);
    }

    @Test
    public void whenRequestingAFloatAndValueIsAFloatThenItIsReturned() {
        assertEquals(15f, objectToFloat("key", 15f), 0);
    }

    @Test
    public void whenRequestingABooleanAndValueIsABooleanThenItIsReturned() {
        assertEquals(false, objectToBoolean("key", false));
    }

    @Test
    public void whenRequestingAStringListAndValueIsAStringListThenItIsReturned() {
        List<String> list = Arrays.asList("a", "b");

        List<String> value = objectToList("key", list, String.class);

        assertEquals(list, value);
    }

    @Test
    public void whenRequestingAnIntegerThenANumericValueIsConverted() {
        assertEquals(new Integer(123), objectToInteger("key", "123"));
    }

    @Test
    public void whenRequestingALongThenANumericValueIsConverted() {
        assertEquals(new Long(123123), objectToLong("key", "123123"));
    }

    @Test
    public void whenRequestingADoubleThenANumericValueIsConverted() {
        assertEquals(123.123, objectToDouble("key", "123.123"), 0);
    }

    @Test
    public void whenRequestingAFloatThenANumericValueIsConverted() {
        assertEquals(123.123f, objectToFloat("key", "123.123"), 0);
    }

    @Test
    public void whenRequestingAUriThenAUriValueIsConverted() {
        URI uri = objectToUri("key", "urn:isbn:0-486-27557-4");
        assertEquals("urn", uri.getScheme());
        assertEquals("isbn:0-486-27557-4", uri.getSchemeSpecificPart());
    }

    @Test
    public void whenRequestingAUrlThenAUrlValueIsConverted() {
        URL url = objectToUrl("key", "http://localhost:8080/abc");
        assertEquals("http", url.getProtocol());
        assertEquals("localhost", url.getHost());
        assertEquals(8080, url.getPort());
        assertEquals("/abc", url.getPath());
    }

    @Test
    public void whenRequestingAPathThenAPathValueIsConverted() {
        Path path = objectToPath("key", "a/b/c");
        assertEquals("a" + File.separator + "b" + File.separator + "c", path.toString());
    }

    enum X {A, B_B, C}

    @Test
    public void whenRequestingAnEnumThenAnEumValueIsConverted() {
        assertEquals(X.A, objectToEnum("key", "A", X.class));
        assertEquals(X.B_B, objectToEnum("key", " 'b b' ", X.class));
    }

    @Test
    public void whenRequestingABooleanThenABooleanValueIsConverted() {
        assertEquals(true, objectToBoolean("key", "true"));
        assertEquals(false, objectToBoolean("key", "n"));
    }

    @Test
    public void whenRequestingAStringListThenAStringIsSplitOnCommas() {
        List<String> list = objectToList("key", "henk, piet, klaas", String.class);

        assertEquals(3, list.size());
        assertEquals("henk", list.get(0));
        assertEquals("piet", list.get(1));
        assertEquals("klaas", list.get(2));
    }

    @Test(expected = ParameterSourceException.class)
    public void whenRequestingAnIntegerThenABadValueFails() {
        objectToInteger("key", "qqqqq");
    }

    @Test
    public void whenRequestingAClassThenItReturnsAClass() {
        assertEquals(Integer.class, objectToClass("key", "java.lang.Integer"));
    }

    @Test
    public void unobfuscateAString() {
        assertEquals("some string", unobfuscateString("key", "c29tZSBzdHJpbmc="));
    }
}
