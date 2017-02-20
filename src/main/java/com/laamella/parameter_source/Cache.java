package com.laamella.parameter_source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface Cache {
    <T> T get(String key, Function<String, T> freshValueSource);

    void flush();

    /**
     * A <b>very</b> basic cache.
     */
    class Default implements Cache {
        private static final Logger logger = LoggerFactory.getLogger(Cache.Default.class);

        private final Map<String, Object> content = new HashMap<>();
        private final long flushInterval;
        private long flushTime;

        public Default(Duration flushInterval) {
            this.flushInterval = flushInterval.toNanos();
            flushTime = System.nanoTime() + this.flushInterval;
        }

        public <T> T get(String key, Function<String, T> freshValueSource) {
            final long now = System.nanoTime();
            if (now > flushTime) {
                flush();
                flushTime = now + flushInterval;
            }
            final T v = (T) content.get(key);
            if (v == null) {
                final T newValue;
                if ((newValue = freshValueSource.apply(key)) != null) {
                    logger.debug("Cache miss for {}.", key);
                    content.put(key, newValue);
                    return newValue;
                }
            }

            if (v == null) {
                logger.debug("Not caching null for {}.", key);
            } else {
                logger.debug("Cache hit for `{}`: `{}`.", key, v);
            }

            return v;
        }

        public void flush() {
            content.clear();
        }

        @Override
        public String toString() {
            return String.format("cache with %s nanosecond flush interval", flushInterval);
        }
    }

}