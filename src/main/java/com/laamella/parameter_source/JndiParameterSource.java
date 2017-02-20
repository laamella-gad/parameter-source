package com.laamella.parameter_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Uses a JNDI InitialContext as the parameter source.
 */
public class JndiParameterSource implements ParameterSource {
    private static final Logger logger = LoggerFactory.getLogger(JndiParameterSource.class);

    private final InitialContext initialContext;

    public JndiParameterSource() throws NamingException {
        this(new InitialContext());
    }

    public JndiParameterSource(InitialContext initialContext) {
        requireNonNull(initialContext);
        this.initialContext = initialContext;
        logger.info("Creating a {}.", toString());

    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        requireNonNull(key);

        try {
            return log(key, Optional.ofNullable(initialContext.lookup(key)));
        } catch (NamingException e) {
            // treat this case as "not found"
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "JNDI parameter source";
    }
}
