package com.laamella.parameter_source;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static com.laamella.parameter_source.ParameterSourceException.missingKeyException;
import static java.util.Objects.requireNonNull;

/**
 * Abstract interface to a parameter (simple key-value) store, adds checking of required parameters, a tight interface,
 * logging, and easy testing.
 */
public interface ParameterSource {
    /**
     * Retrieves a String from this source by key.
     */
    Optional<String> getOptionalString(String key);

    /**
     * Retrieves a list of Strings from this source by key.
     */
    Optional<List<String>> getOptionalStringList(String key);

    /**
     * Retrieves an optional Integer from this source by key.
     */
    Optional<Integer> getOptionalInteger(String key);

    /**
     * Retrieves an optional Long from this source by key.
     */
    Optional<Long> getOptionalLong(String key);

    /**
     * Retrieves an optional Float from this source by key.
     */
    Optional<Float> getOptionalFloat(String key);

    /**
     * Retrieves an optional Double from this source by key.
     */
    Optional<Double> getOptionalDouble(String key);

    /**
     * Retrieves an optional boolean from this source by key.
     */
    Optional<Boolean> getOptionalBoolean(String key);

    /**
     * Retrieves an optional Object from this source by key.
     */
    Optional<Object> getOptionalObject(String key);

    /**
     * Retrieves an optional Class from this source by key.
     */
    Optional<Class<?>> getOptionalClass(String key);

    /**
     * Retrieves an optional URL from this source by key.
     */
    Optional<URL> getOptionalUrl(String key);

    /**
     * Retrieves an optional URI from this source by key.
     */
    Optional<URI> getOptionalUri(String key);

    /**
     * Retrieves an optional enum from this source by key.
     */
    <T extends Enum<?>> Optional<T> getOptionalEnum(String key, Class<T> enumType);

    /**
     * Retrieves an optional duration from this source by key.
     */
    default Optional<Duration> getOptionalDuration(String key) {
        return getOptionalString(key).map(s -> TypeConverter.stringToDuration(key, s));
    }

    /**
     * Creates a parameter source that takes its values from this
     * parameter source.
     * If values are missing, it looks in the fallback instead.
     * <p>This can be chained to create a fallback chain.
     */
    default FallbackParameterSource withFallback(ParameterSource fallback) {
        requireNonNull(fallback);
        return new FallbackParameterSource(this, fallback);
    }

    /**
     * Retrieves a required String from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default String getString(String key) {
        requireNonNull(key);
        return getOptionalString(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a list of Strings from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default List<String> getStringList(String key) {
        requireNonNull(key);
        return getOptionalStringList(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required int from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default int getInteger(String key) {
        requireNonNull(key);
        return getOptionalInteger(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required long from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default long getLong(String key) {
        requireNonNull(key);
        return getOptionalLong(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required float from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default float getFloat(String key) {
        requireNonNull(key);
        return getOptionalFloat(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required double from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default double getDouble(String key) {
        requireNonNull(key);
        return getOptionalDouble(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required boolean from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default boolean getBoolean(String key) {
        requireNonNull(key);
        return getOptionalBoolean(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required duration from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default Duration getDuration(String key) {
        requireNonNull(key);
        return getOptionalDuration(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required URL from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default URL getUrl(String key) {
        requireNonNull(key);
        return getOptionalUrl(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required URI from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default URI getUri(String key) {
        requireNonNull(key);
        return getOptionalUri(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required URI from this source by key.
     *
     * @throws ParameterSourceException when the key is missing.
     */
    default <T extends Enum<?>> T getEnum(String key, Class<T> enumType) {
        requireNonNull(key);
        return getOptionalEnum(key, enumType).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required object from this source by key.
     */
    default Object getObject(String key) {
        requireNonNull(key);
        return getOptionalObject(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Retrieves a required class from this source by key.
     */
    default Class<?> getClass(String key) {
        requireNonNull(key);
        return getOptionalClass(key).orElseThrow(missingKeyException(key));
    }

    /**
     * Creates a parameter source that prepends "keyPart" to every key requested.
     * This can be chained to go deeper and deeper.
     * Note that a "." between "keyPart" and requested keys is enforced.
     */
    default SubParameterSource subSource(String keyPart) {
        requireNonNull(keyPart);
        return new SubParameterSource(this, keyPart, getPathSeparator());
    }

    /**
     * @return the String that separates the parts of a key into a hierarchy.
     */
    default String getPathSeparator() {
        return "/";
    }
}
