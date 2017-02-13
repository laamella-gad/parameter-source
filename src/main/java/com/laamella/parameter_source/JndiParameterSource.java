package com.laamella.parameter_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Optional;

/**
 * Uses a JNDI InitialContext as the parameter store.
 */
public class JndiParameterSource extends ParameterSource<Object> {
    private final Logger logger = LoggerFactory.getLogger(JndiParameterSource.class);

    private final InitialContext initialContext;

    public JndiParameterSource() throws NamingException {
        this(new InitialContext());
    }

    public JndiParameterSource(InitialContext initialContext) {
        this.initialContext = initialContext;
    }

    @Override
    protected Optional<Object> getOptionalValueFromSource(String key) {
        try {
            return Optional.ofNullable(initialContext.lookup(key));
        } catch (NamingException e) {
            logger.debug(String.format("Parameter %s not found", key), e);
            return Optional.empty();
        }
    }
}
