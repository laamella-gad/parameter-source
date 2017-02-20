package com.laamella.parameter_source;

import java.io.UnsupportedEncodingException;
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

    public static Duration stringToDuration(String key, String s) {
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
        throw new ParameterSourceException("Duration %s for key %s cannot be parsed.", s, key);
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

    public static int stringToInteger(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not an integer.", str, key);
        }
    }

    public static <T> List<T> stringToList(String key, String input, Function<String, T> itemConverter) {
        requireNonNull(key);
        requireNonNull(input);
        requireNonNull(itemConverter);
        final List<T> result = new ArrayList<>();
        for (String itemString : input.split(",")) {
            result.add(itemConverter.apply(itemString.trim()));
        }
        return result;
    }

    public static long stringToLong(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a long.", str, key);
        }
    }

    public static URL stringToUrl(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        try {
            return new URI(str).toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            throw new ParameterSourceException("Value %s of %s is not a URL.", str, key);
        }
    }

    public static URI stringToUri(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        try {
            return new URI(str);
        } catch (URISyntaxException e) {
            throw new ParameterSourceException("Value %s of %s is not a URI.", str, key);
        }
    }

    public static Path stringToPath(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        try {
            return Paths.get(str);
        } catch (InvalidPathException e) {
            throw new ParameterSourceException("Value %s of %s is not a path.", str, key);
        }
    }

    public static float stringToFloat(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a float.", str, key);
        }
    }

    public static double stringToDouble(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a double.", str, key);
        }
    }

    public static boolean stringToBoolean(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        switch (str.toLowerCase()) {
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
                throw new ParameterSourceException("Value %s of %s is not a boolean.", str, key);
        }
    }

    public static Object stringToObject(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        return str;
    }

    public static Class<?> stringToClass(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new ParameterSourceException("Value %s if %s is not a class.", str, key);
        }
    }

    public static <T extends Enum<?>> T stringToEnum(String key, String str, Class<T> enumType) {
        requireNonNull(key);
        requireNonNull(str);
        for (T t : enumType.getEnumConstants()) {
            if (fuzzy(t.name()).equals(fuzzy(str))) {
                return t;
            }
        }
        throw new ParameterSourceException("Value %s of %s is not a %s.", str, key, enumType.getSimpleName());
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

    public static String stringToUnobfuscatedString(String key, String str) {
        requireNonNull(key);
        requireNonNull(str);
        try {
            return new String(Base64.getDecoder().decode(str), "ASCII");
        } catch (UnsupportedEncodingException | IllegalArgumentException e) {
            throw new ParameterSourceException(e, "Value %s of %s cannot be base64 decoded.", str, key);
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
            return stringToInteger(key, (String) o);
        }
        throw new ParameterSourceException("%s does not contain an integer value.", key);
    }

    public static <T> List<T> objectToList(String key, Object o, Class<T> type) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof List) {
            // TODO we could check if there are really objects of type "type" in the list
            return (List<T>) o;
        }
        throw new ParameterSourceException("%s does not contain a list value.", key);
    }

    public static Long objectToLong(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Long) {
            return (Long) o;
        }
        if (o instanceof String) {
            return stringToLong(key, (String) o);
        }
        throw new ParameterSourceException("%s does not contain an integer value.", key);
    }

    public static Float objectToFloat(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Float) {
            return (Float) o;
        }
        if (o instanceof String) {
            return stringToFloat(key, (String) o);
        }
        throw new ParameterSourceException("%s does not contain a float value.", key);
    }

    public static Double objectToDouble(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Double) {
            return (Double) o;
        }
        if (o instanceof String) {
            return stringToDouble(key, (String) o);
        }
        throw new ParameterSourceException("%s does not contain a double value.", key);
    }

    public static Boolean objectToBoolean(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        if (o instanceof String) {
            return stringToBoolean(key, (String) o);
        }
        throw new ParameterSourceException("%s does not contain a boolean value.", key);
    }

    public static URI objectToUri(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof URI) {
            return (URI) o;
        }
        if (o instanceof String) {
            return stringToUri(key, (String) o);
        }
        throw new ParameterSourceException("%s does not contain a URI value.", key);
    }

    public static URL objectToUrl(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof URL) {
            return (URL) o;
        }
        if (o instanceof String) {
            return stringToUrl(key, (String) o);
        }
        throw new ParameterSourceException("%s does not contain a URL value.", key);
    }

    public static Path objectToPath(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Path) {
            return (Path) o;
        }
        if (o instanceof String) {
            return stringToPath(key, (String) o);
        }
        throw new ParameterSourceException("%s does not contain a URL value.", key);
    }

    public static <T extends Enum> T objectToEnum(String key, Object o, Class<T> enumType) {
        requireNonNull(key);
        requireNonNull(o);
        if (enumType.isInstance(o)) {
            return enumType.cast(o);
        }
        if (o instanceof String) {
            return stringToEnum(key, (String) o, enumType);
        }
        throw new ParameterSourceException("%s does not contain a %s value.", key, enumType.getSimpleName());
    }

    public static Class<?> objectToClass(String key, Object o) {
        requireNonNull(key);
        requireNonNull(o);
        if (o instanceof Class) {
            return (Class<?>) o;
        }
        if (o instanceof String) {
            return stringToClass(key, (String) o);
        }
        throw new ParameterSourceException("%s does not contain a Class value.", key);
    }
}
