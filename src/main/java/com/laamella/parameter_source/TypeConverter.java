package com.laamella.parameter_source;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * All type conversion code
 */
public class TypeConverter {

    private static final Pattern P_DURATION_PATTERN = Pattern.compile(
            "^" +
                    "(?:(\\d+)[Dd])?\\s*" +
                    "(?:(\\d+)[Hh])?\\s*" +
                    "(?:(\\d+)[Mm])?\\s*" +
                    "(?:(\\d+)[Ss])?\\s*" +
                    "(?:(\\d+)[Mm][Ss])?\\s*" +
                    "(?:(\\d+)[Nn][Ss])?\\s*" +
                    "$");

    private static final Pattern SEMICOLONS_DURATION_PATTERN = Pattern.compile("^(?:(?:(\\d*):)?(\\d*):)?(\\d*)\\.?(\\d*)$");

    public static Duration stringToDuration(String key, String s) {
        final Matcher isoMatcher = P_DURATION_PATTERN.matcher(s);
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
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not an integer.", str, key);
        }
    }

    public static <T> List<T> stringToList(String key, String input, Function<String, T> itemConverter) {
        final List<T> result = new ArrayList<>();
        for (String itemString : input.split(",")) {
            result.add(itemConverter.apply(itemString.trim()));
        }
        return result;
    }

    public static long stringToLong(String key, String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a long.", str, key);
        }
    }

    public static float stringToFloat(String key, String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a float.", str, key);
        }
    }

    public static double stringToDouble(String key, String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new ParameterSourceException("Value %s of %s is not a double.", str, key);
        }
    }

    public static boolean stringToBoolean(String key, String str) {
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
        return str;
    }

    public static String objectToString(String key, Object o) {
        return o.toString();
    }

    public static Integer objectToInteger(String key, Object o) {
        if (o instanceof Integer) {
            return (Integer) o;
        }
        throw new ParameterSourceException("%s does not contain an integer value.", key);
    }

    public static <T> List<T> objectToList(String key, Object o, Class<T> type) {
        if (o instanceof List) {
            // TODO we could check if there are really objects of type "type" in the list
            return (List<T>) o;
        }
        throw new ParameterSourceException("%s does not contain a list value.", key);
    }

    public static Long objectToLong(String key, Object o) {
        if (o instanceof Long) {
            return (Long) o;
        }
        throw new ParameterSourceException("%s does not contain an integer value.", key);
    }

    public static Float objectToFloat(String key, Object o) {
        if (o instanceof Float) {
            return (Float) o;
        }
        throw new ParameterSourceException("%s does not contain a float value.", key);
    }

    public static Double objectToDouble(String key, Object o) {
        if (o instanceof Double) {
            return (Double) o;
        }
        throw new ParameterSourceException("%s does not contain a double value.", key);
    }

    public static Boolean objectToBoolean(String key, Object o) {
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        throw new ParameterSourceException("%s does not contain a boolean value.", key);
    }

}
