package com.laamella.parameter_source;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.laamella.parameter_source.ParameterSourceException.badValueException;
import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.util.Objects.requireNonNull;

/**
 * All type conversion code
 */
public class TypeConverter {

    private static final Pattern P_DURATION_PATTERN = Pattern.compile(
            "^" +
                    "(?:(\\d+)\\s*(?:d|days?))?\\s*" +
                    "(?:(\\d+)\\s*(?:h|hours?))?\\s*" +
                    "(?:(\\d+)\\s*(?:m|minutes?))?\\s*" +
                    "(?:(\\d+)\\s*(?:s|seconds?))?\\s*" +
                    "(?:(\\d+)\\s*(?:ms|milliseconds?))?\\s*" +
                    "(?:(\\d+)\\s*(?:ns|nanoseconds?))?\\s*" +
                    "$");

    private static final Pattern SEMICOLONS_DURATION_PATTERN = Pattern.compile("^(?:(?:(\\d*):)?(\\d*):)?(\\d*)\\.?(\\d*)$");

    public static Duration parseDuration(String key, String s) {
        requireNonNull(key);
        requireNonNull(s);

        final Matcher isoMatcher = P_DURATION_PATTERN.matcher(s.toLowerCase());
        if (isoMatcher.matches()) {
            return
                    add(isoMatcher.group(1), Duration::plusDays,
                            add(isoMatcher.group(2), Duration::plusHours,
                                    add(isoMatcher.group(3), Duration::plusMinutes,
                                            add(isoMatcher.group(4), Duration::plusSeconds,
                                                    add(isoMatcher.group(5), Duration::plusMillis,
                                                            add(isoMatcher.group(6), Duration::plusNanos,
                                                                    Duration.ofMillis(0)))))));
        }

        final Matcher semiMatcher = SEMICOLONS_DURATION_PATTERN.matcher(s);
        if (semiMatcher.matches()) {
            return
                    add(semiMatcher.group(1), Duration::plusHours,
                            add(semiMatcher.group(2), Duration::plusMinutes,
                                    add(semiMatcher.group(3), Duration::plusSeconds,
                                            add(semiMatcher.group(4), TypeConverter::nanoFraction,
                                                    Duration.ofMillis(0)))));
        }
        throw badValueException(key, s, "a duration").get();
    }

    private static Duration nanoFraction(Duration durationIn, long fraction) {
        long nanos = Math.round(Double.parseDouble("0." + fraction) * 1000000000);
        return durationIn.plusNanos(nanos);
    }

    private static Duration add(String stringValue, BiFunction<Duration, Long, Duration> adder, Duration durationIn) {
        if (stringValue == null || stringValue.isEmpty()) {
            return durationIn;
        }
        return adder.apply(durationIn, Long.parseLong(stringValue));
    }

    public static <T> List<T> parseList(String key, String input, Function<String, T> itemConverter) {
        requireNonNull(key);
        requireNonNull(input);
        requireNonNull(itemConverter);
        final List<T> result = new ArrayList<>();
        for (String itemString : input.split(",")) {
            result.add(itemConverter.apply(itemString.trim()));
        }
        return result;
    }

    private static String fuzzy(String str) {
        return str
                .toLowerCase()
                .replace("_", "")
                .replace("\t", "")
                .replace("\"", "")
                .replace("'", "")
                .replace(" ", "");
    }

    public static String unobfuscateString(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        try {
            return new String(Base64.getDecoder().decode(str), US_ASCII);
        } catch (IllegalArgumentException e) {
            throw badValueException(key, str, "base 64 encoded").get();
        }
    }

    public static String objectToString(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        return o.toString();
    }

    public static Integer objectToInteger(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Integer) {
            return (Integer) o;
        }
        if (o instanceof String) {
            requireNonNull(key);
            requireNonNull((String) o);
            try {
                return Integer.parseInt((String) o);
            } catch (NumberFormatException e) {
                // fall through to exception
            }
        }
        throw badValueException(key, "an integer").get();
    }

    public static <T> List<T> objectToList(String key, Object o, Class<T> type) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof List) {
            // TODO we could check if there are really objects of type "type" in the list
            return (List<T>) o;
        }
        if (type == String.class) {
            return parseList(key, (String) o, i -> (T) i);
        }
        throw badValueException(key, "a list").get();
    }

    public static Long objectToLong(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Long) {
            return (Long) o;
        }
        if (o instanceof String) {
            requireNonNull(key);
            requireNonNull((String) o);
            try {
                return Long.parseLong((String) o);
            } catch (NumberFormatException e) {
                // fall through to exception
            }
        }
        throw badValueException(key, "a long").get();
    }

    public static Float objectToFloat(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Float) {
            return (Float) o;
        }
        if (o instanceof String) {
            requireNonNull(key);
            requireNonNull((String) o);
            try {
                return Float.parseFloat((String) o);
            } catch (NumberFormatException e) {
                // fall through to exception
            }
        }
        throw badValueException(key, "a float").get();
    }

    public static Double objectToDouble(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Double) {
            return (Double) o;
        }
        if (o instanceof String) {
            requireNonNull(key);
            requireNonNull((String) o);
            try {
                return Double.parseDouble((String) o);
            } catch (NumberFormatException e) {
                // fall through to exception
            }
        }
        throw badValueException(key, "a double").get();
    }

    public static Boolean objectToBoolean(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        if (o instanceof String) {
            requireNonNull(key);
            requireNonNull((String) o);
            switch (((String) o).toLowerCase()) {
                case "true":
                case "t":
                case "y":
                case "yes":
                case "1":
                case "enable":
                case "enabled":
                    return true;
                case "false":
                case "f":
                case "n":
                case "no":
                case "0":
                case "disable":
                case "disabled":
                    return false;
                default:
                    // fall through to exception
            }
        }
        throw badValueException(key, "a boolean").get();
    }

    public static URI objectToUri(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof URI) {
            return (URI) o;
        }
        if (o instanceof String) {
            requireNonNull(key);
            requireNonNull((String) o);
            try {
                return new URI((String) o);
            } catch (URISyntaxException e) {
                // fall through to exception
            }
        }
        throw badValueException(key, "a URI").get();
    }

    public static URL objectToUrl(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof URL) {
            return (URL) o;
        }
        if (o instanceof String) {
            requireNonNull(key);
            requireNonNull((String) o);
            try {
                return new URI((String) o).toURL();
            } catch (URISyntaxException | MalformedURLException e) {
                // fall through to exception
            }
        }
        throw badValueException(key, "a URL").get();
    }

    public static Path objectToPath(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Path) {
            return (Path) o;
        }
        if (o instanceof String) {
            requireNonNull(key);
            requireNonNull((String) o);
            try {
                return Paths.get((String) o);
            } catch (InvalidPathException e) {
                // fall through to exception
            }
        }
        throw badValueException(key, "a path").get();
    }

    public static <T extends Enum<?>> T objectToEnum(String key, Object o, Class<T> enumType) {
        requireNonNull(key);
        requireNonNull(o);
        if (enumType.isInstance(o)) {
            return enumType.cast(o);
        }
        if (o instanceof String) {
            requireNonNull(key);
            requireNonNull((String) o);
            for (T t : enumType.getEnumConstants()) {
                if (fuzzy(t.name()).equals(fuzzy((String) o))) {
                    return t;
                }
            }
        }
        throw badValueException(key, "a "+enumType.getSimpleName()).get();
    }

    public static Class<?> objectToClass(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Class) {
            return (Class<?>) o;
        }
        if (o instanceof String) {
            requireNonNull(key);
            requireNonNull((String) o);
            try {
                return Class.forName((String) o);
            } catch (ClassNotFoundException e) {
                // fall through to exception
            }
        }
        throw badValueException(key, "a Class").get();
    }
}
