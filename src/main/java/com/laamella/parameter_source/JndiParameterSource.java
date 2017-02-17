package com.laamella.parameter_source;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * Uses a JNDI InitialContext as the parameter source.
 */
public class JndiParameterSource implements ParameterSource {
    private final InitialContext initialContext;

    public JndiParameterSource() throws NamingException {
        this(new InitialContext());
    }

    public JndiParameterSource(InitialContext initialContext) {
        requireNonNull(initialContext);
        this.initialContext = initialContext;
    }

    @Override
    public Optional<Object> getOptionalObject(String key) {
        requireNonNull(key);

        try {
            final Object value = initialContext.lookup(key);
            return Optional.ofNullable(value);
        } catch (NamingException e) {
            // treat this case as "not found"
        } catch (ClassCastException cce) {
            throw new ParameterSourceException(cce, "Value found for %s was not of the requested type.", key);
        }
        return Optional.empty();
    }
}
