package com.laamella.parameter_source;

import org.junit.Test;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JndiParameterSourceTest {
    private final InitialContext initialContextMock = mock(InitialContext.class);
    private final JndiParameterSource jndiParameterSource = new JndiParameterSource(initialContextMock);

    @Test
    public void whenRequiredParameterExistsThenReturnIt() throws MissingParameterException, NamingException {
        when(initialContextMock.lookup("abc")).thenReturn("def");
        String value = jndiParameterSource.getParameter("abc");
        assertThat(value).isEqualTo("def");
    }

    @Test(expected = MissingParameterException.class)
    public void whenRequiredParameterIsNullThenException() throws MissingParameterException, NamingException {
        when(initialContextMock.lookup("abc")).thenReturn(null);
        jndiParameterSource.getParameter("abc");
    }

    @Test(expected = MissingParameterException.class)
    public void whenRequiredParameterIsNotFoundThenException() throws MissingParameterException, NamingException {
        when(initialContextMock.lookup("abc")).thenThrow(new NamingException());
        jndiParameterSource.getParameter("abc");
    }

    @Test
    public void whenOptionalParameterExistsThenReturnIt() throws MissingParameterException, NamingException {
        when(initialContextMock.lookup("abc")).thenReturn("def");
        String value = jndiParameterSource.getParameter("abc", "xyz");
        assertThat(value).isEqualTo("def");
    }

    @Test
    public void whenOptionalParameterIsNullThenReturnDefault() throws MissingParameterException, NamingException {
        when(initialContextMock.lookup("abc")).thenReturn(null);
        String value = jndiParameterSource.getParameter("abc", "xyz");
        assertThat(value).isEqualTo("xyz");
    }


}
