package com.laamella.parameter_source;

import org.junit.Test;

import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static com.laamella.parameter_source.TypeConverter.*;
import static org.junit.Assert.assertEquals;

public class TypeConverterTest {
    @Test
    public void durationsInPseudoIso8601() {
        assertEquals("PT0.001S", stringToDuration("", "1ms").toString());
        assertEquals("PT0.000000001S", stringToDuration("", "1ns").toString());
        assertEquals("PT120H1S", stringToDuration("", "5D 1S").toString());
        assertEquals("PT51H4M5.006000007S", stringToDuration("", "2d 3h 4m 5s 6ms 7ns").toString());
    }

    @Test
    public void durationsWithSemicolons() {
        assertEquals("PT10S", stringToDuration("", ":10").toString());
        assertEquals("PT10M10S", stringToDuration("", "10:10").toString());
        assertEquals("PT14H12M10S", stringToDuration("", "14:12:10").toString());
        assertEquals("PT14H12M10.123S", stringToDuration("", "14:12:10.123").toString());
        assertEquals("PT0S", stringToDuration("", "0").toString());
        assertEquals("PT12.345S", stringToDuration("", "12.345").toString());
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
        assertEquals(123, stringToInteger("key", "123"));
    }

    @Test
    public void whenRequestingALongThenANumericValueIsConverted() {
        assertEquals(123123, stringToLong("key", "123123"));
    }

    @Test
    public void whenRequestingADoubleThenANumericValueIsConverted() {
        assertEquals(123.123, stringToDouble("key", "123.123"), 0);
    }

    @Test
    public void whenRequestingAFloatThenANumericValueIsConverted() {
        assertEquals(123.123f, stringToFloat("key", "123.123"), 0);
    }

    @Test
    public void whenRequestingAUriThenAUriValueIsConverted() {
        URI uri = stringToUri("key", "urn:isbn:0-486-27557-4");
        assertEquals("urn", uri.getScheme());
        assertEquals("isbn:0-486-27557-4", uri.getSchemeSpecificPart());
    }

    @Test
    public void whenRequestingAUrlThenAUrlValueIsConverted() {
        URL url = stringToUrl("key", "http://localhost:8080/abc");
        assertEquals("http", url.getProtocol());
        assertEquals("localhost", url.getHost());
        assertEquals(8080, url.getPort());
        assertEquals("/abc", url.getPath());
    }

    enum X {A, B_B, C}

    @Test
    public void whenRequestingAnEnumThenAnEumValueIsConverted() {
        assertEquals(X.A, stringToEnum("key", "A", X.class));
        assertEquals(X.B_B, stringToEnum("key", " 'b b' ", X.class));
    }

    @Test
    public void whenRequestingABooleanThenABooleanValueIsConverted() {
        assertEquals(true, stringToBoolean("key", "true"));
        assertEquals(false, stringToBoolean("key", "n"));
    }

    @Test
    public void whenRequestingAStringListThenAStringIsSplitOnCommas() {
        List<String> list = stringToList("key", "henk, piet, klaas", i -> i);

        assertEquals(3, list.size());
        assertEquals("henk", list.get(0));
        assertEquals("piet", list.get(1));
        assertEquals("klaas", list.get(2));
    }

    @Test(expected = ParameterSourceException.class)
    public void whenRequestingAnIntegerThenABadValueFails() {
        stringToInteger("key", "qqqqq");
    }

    @Test
    public void whenRequestingAnObjectThenItOnlyReturnsStrings() {
        assertEquals("qqqqq", stringToObject("key", "qqqqq"));
    }

    @Test
    public void whenRequestingAClassThenItReturnsAClass() {
        assertEquals(Integer.class, stringToClass("key", "java.lang.Integer"));
    }
}
