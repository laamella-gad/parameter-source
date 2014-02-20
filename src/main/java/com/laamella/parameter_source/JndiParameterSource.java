package com.laamella.parameter_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Uses a JNDI InitialContext as the parameter store.
 */
public class JndiParameterSource extends ParameterSource {
    private final Logger logger = LoggerFactory.getLogger(JndiParameterSource.class);

    private final InitialContext initialContext;

    public JndiParameterSource() throws NamingException {
        this(new InitialContext());
    }

    public JndiParameterSource(InitialContext initialContext) {
        this.initialContext = initialContext;
    }

    @Override
    protected <T> T getRawParameter(String key) {
        try {
            return (T) initialContext.lookup(key);
        } catch (NamingException e) {
            logger.debug("Parameter not found", e);
            return null;
        }
    }
}
