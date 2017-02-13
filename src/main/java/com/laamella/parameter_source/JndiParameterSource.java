package com.laamella.parameter_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Optional;

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
    public Optional<String> getOptionalString(String key) {
        return getOptionalObject(key, String.class);
    }

    @Override
    public Optional<Integer> getOptionalInteger(String key) {
        return getOptionalObject(key, int.class);
    }

    public <T> T getObject(String key, Class<T> type) {
        return getOptionalObject(key, type).orElseThrow(missingKeyException(key));
    }

    public <T> Optional<T> getOptionalObject(String key, Class<T> type) {
        try {
            return Optional.ofNullable(type.cast(initialContext.lookup(key)));
        } catch (NamingException e) {
            logger.debug(String.format("Parameter %s not found", key), e);
            return Optional.empty();
        }
    }
}
