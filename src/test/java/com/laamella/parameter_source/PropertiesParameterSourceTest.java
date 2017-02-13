package com.laamella.parameter_source;

import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class PropertiesParameterSourceTest {
    private final PropertiesParameterSource propertiesParameterSource = new PropertiesParameterSource("test.properties");

    @Test
    public void whenWeGetAKeyThenWeGetAValue() throws IOException {
        Optional<String> abc = propertiesParameterSource.getOptionalValueFromSource("abc");

        assertEquals("def", abc.get());
    }

    @Test
    public void whenWeGetAKeyThatDoesNotExistThenWeDontGetAValue() throws IOException {
        Optional<String> abc = propertiesParameterSource.getOptionalValueFromSource("qoqoq");

        assertEquals(false, abc.isPresent());
    }

}
