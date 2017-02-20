package com.laamella.parameter_source;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Stores a hostname or IP address, and an optional port.
 */
public class HostAndPort {
    private final String host;
    private final Optional<Integer> port;

    private static final Pattern PATTERN = Pattern.compile("^([^:]*)(?::(\\d+))?$");

    public HostAndPort(String host, Optional<Integer> port) {
        this.host = host;
        this.port = port;
    }

    public Optional<Integer> getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public static Optional<HostAndPort> parse(String str) {
        Matcher matcher = PATTERN.matcher(str);
        if (matcher.matches()) {
            if (matcher.group(2) == null) {
                return Optional.of(new HostAndPort(matcher.group(1), Optional.empty()));
            }
            return Optional.of(new HostAndPort(matcher.group(1), Optional.of(Integer.parseInt(matcher.group(2)))));
        }
        return Optional.empty();
    }
}
