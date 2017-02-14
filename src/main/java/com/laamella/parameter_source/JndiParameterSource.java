package com.laamella.parameter_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.*;

/**
 * Uses a JNDI InitialContext as the parameter store.
 */
public class JndiParameterSource implements ObjectParameterSource {
    private final Logger logger = LoggerFactory.getLogger(JndiParameterSource.class);

    private final InitialContext initialContext;

    public JndiParameterSource() throws NamingException {
        this(new InitialContext());
    }

    public JndiParameterSource(InitialContext initialContext) {
        requireNonNull(initialContext);
        this.initialContext = initialContext;
    }

    @Override
    public <T> Optional<T> getOptionalObject(String key, Class<T> type) {
        requireNonNull(key);
        requireNonNull(type);

        try {
            return Optional.ofNullable(type.cast(initialContext.lookup(key)));
        } catch (NamingException e) {
            logger.debug(String.format("Parameter %s not found", key), e);
            return Optional.empty();
        }
    }
}
