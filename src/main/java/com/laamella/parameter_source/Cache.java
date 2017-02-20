package com.laamella.parameter_source;

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
            return (T) content.computeIfAbsent(key, freshValueSource);
        }

        public void flush() {
            content.clear();
        }
    }

}