package com.laamella.parameter_source;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Stores a hostname or IP address, and an optional port.
 */
public class HostAndPort {
    private final String host;
    private final Integer port;

    private static final Pattern PATTERN = Pattern.compile("^([^:]*)(?::(\\d+))?$");

    public HostAndPort(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public static Optional<HostAndPort> parse(String str) {
        Matcher matcher = PATTERN.matcher(str);
        if (matcher.matches()) {
            if (matcher.group(2) == null) {
                return Optional.of(new HostAndPort(matcher.group(1), null));
            }
            return Optional.of(new HostAndPort(matcher.group(1), Integer.parseInt(matcher.group(2))));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        if (port == null) {
            return host + ":" + port;
        }
        return host;
    }
}
