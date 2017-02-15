package com.laamella.parameter_source;

import org.junit.Test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JndiParameterSourceTest {
    private final InitialContext initialContextMock = mock(InitialContext.class);
    private final JndiParameterSource source = new JndiParameterSource(initialContextMock);

    @Test
    public void whenRequiredParameterExistsThenReturnIt() throws NamingException {
        when(initialContextMock.lookup("abc")).thenReturn("def");
        Optional<String> value = source.getOptionalString("abc");
        assertEquals("def", value.get());
    }

    @Test
    public void whenRequiredParameterIsNullThenException() throws NamingException {
        when(initialContextMock.lookup("abc")).thenReturn(null);
        Optional<String> abc = source.getOptionalString("abc");
        assertEquals(false, abc.isPresent());
    }

    @Test
    public void whenRequiredParameterIsNotFoundThenException() throws NamingException {
        when(initialContextMock.lookup("abc")).thenThrow(new NamingException());
        Optional<String> abc = source.getOptionalString("abc");
        assertEquals(false, abc.isPresent());
    }

    @Test(expected = ParameterSourceException.class)
    public void whenObjectIsOfWrongTypeThenItIsNotFound() throws NamingException {
        when(initialContextMock.lookup("abc")).thenReturn("def");
        source.getOptionalInteger("abc");
    }
}
