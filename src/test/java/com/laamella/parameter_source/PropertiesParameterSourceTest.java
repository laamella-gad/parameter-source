package com.laamella.parameter_source;

import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class PropertiesParameterSourceTest {
    private final PropertiesParameterSource propertiesParameterSource = new PropertiesParameterSource("test.properties");

    @Test
    public void whenWeGetAKeyThenWeGetAValue() throws IOException {
        Optional<String> abc = propertiesParameterSource.getOptionalString("abc");

        assertEquals("def", abc.get());
    }

    @Test
    public void whenWeGetAKeyThatDoesNotExistThenWeDontGetAValue() throws IOException {
        Optional<String> abc = propertiesParameterSource.getOptionalString("qoqoq");

        assertEquals(false, abc.isPresent());
    }

    @Test
    public void whenWeSubSourceThePathGetsConstructedCorrectly() {
        final SubParameterSource log4j = propertiesParameterSource.subSource("log4j");

        assertEquals("INFO, stdout", log4j.getString("rootLogger"));

        final ParameterSource stdoutAppender = log4j.subSource("appender.stdout");

        assertEquals("org.apache.log4j.ConsoleAppender", stdoutAppender.getString(""));
        assertEquals("System.out", stdoutAppender.getString("Target"));
        assertEquals("org.apache.log4j.PatternLayout", stdoutAppender.getString("layout"));
    }

}
