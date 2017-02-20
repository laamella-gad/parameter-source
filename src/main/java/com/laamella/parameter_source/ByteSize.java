package com.laamella.parameter_source;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that holds sizes in bytes.
 */
public class ByteSize {
    private static final long KB_SHIFT = 10;
    private static final long MB_SHIFT = 20;
    private static final long GB_SHIFT = 30;
    private static final long TB_SHIFT = 40;
    private static final long PB_SHIFT = 50;
    private static final long EB_SHIFT = 60;

    private final long bytes;

    private static final Pattern PATTERN = Pattern.compile(
            "^" +
                    "(?:(\\d+)\\s*(?:eb|exabytes?))?\\s*" +
                    "(?:(\\d+)\\s*(?:pb|petabytes?))?\\s*" +
                    "(?:(\\d+)\\s*(?:tb|terabytes?))?\\s*" +
                    "(?:(\\d+)\\s*(?:gb|gigabytes?))?\\s*" +
                    "(?:(\\d+)\\s*(?:mb|megabytes?))?\\s*" +
                    "(?:(\\d+)\\s*(?:kb|kilobytes?))?\\s*" +
                    "(?:(\\d+)\\s*(?:b||bytes?))?\\s*" +
                    "$");


    private ByteSize(long bytes) {
        this.bytes = bytes;
    }

    public static ByteSize ofBytes(long amount) {
        return new ByteSize(amount);
    }

    public static ByteSize ofKilobytes(long amount) {
        return new ByteSize(amount << KB_SHIFT);
    }

    public static ByteSize ofMegabytes(long amount) {
        return new ByteSize(amount << MB_SHIFT);
    }

    public static ByteSize ofGigabytes(long amount) {
        return new ByteSize(amount << GB_SHIFT);
    }

    public static ByteSize ofTerabytes(long amount) {
        return new ByteSize(amount << TB_SHIFT);
    }

    public static ByteSize ofPetabytes(long amount) {
        return new ByteSize(amount << PB_SHIFT);
    }

    public static ByteSize ofExabytes(long amount) {
        return new ByteSize(amount << EB_SHIFT);
    }

    public ByteSize plusBytes(long bytes) {
        return new ByteSize(this.bytes + bytes);
    }

    public ByteSize plusKilobytes(long bytes) {
        return plusBytes(bytes << KB_SHIFT);
    }

    public ByteSize plusMegabytes(long bytes) {
        return plusBytes(bytes << MB_SHIFT);
    }

    public ByteSize plusGigabytes(long bytes) {
        return plusBytes(bytes << GB_SHIFT);
    }

    public ByteSize plusTerabytes(long bytes) {
        return plusBytes(bytes << TB_SHIFT);
    }

    public ByteSize plusPetabytes(long bytes) {
        return plusBytes(bytes << PB_SHIFT);
    }

    public ByteSize plusExabytes(long bytes) {
        return plusBytes(bytes << EB_SHIFT);
    }

    public static Optional<ByteSize> parse(String str) {
        Matcher matcher = PATTERN.matcher(str.toLowerCase());
        if (!matcher.matches()) {
            return Optional.empty();
        }

        return Optional.of(
                add(matcher.group(1), ByteSize::plusExabytes,
                        add(matcher.group(2), ByteSize::plusPetabytes,
                                add(matcher.group(3), ByteSize::plusTerabytes,
                                        add(matcher.group(4), ByteSize::plusGigabytes,
                                                add(matcher.group(5), ByteSize::plusMegabytes,
                                                        add(matcher.group(6), ByteSize::plusKilobytes,
                                                                add(matcher.group(7), ByteSize::plusBytes,
                                                                        ByteSize.ofBytes(0)))))))));
    }

    private static ByteSize add(String stringValue, BiFunction<ByteSize, Long, ByteSize> adder, ByteSize in) {
        if (stringValue == null || stringValue.isEmpty()) {
            return in;
        }
        return adder.apply(in, Long.parseLong(stringValue));
    }


    public long getBytes() {
        return bytes;
    }

    public long getKilobytes() {
        return bytes >> KB_SHIFT;
    }

    public long getMegabytes() {
        return bytes >> MB_SHIFT;
    }

    public long getGigabytes() {
        return bytes >> GB_SHIFT;
    }

    public long getTerabytes() {
        return bytes >> TB_SHIFT;
    }

    public long getPetabytes() {
        return bytes >> PB_SHIFT;
    }

    public long getExabytes() {
        return bytes >> EB_SHIFT;
    }

    @Override
    public String toString() {
        if (bytes == 0) {
            return "0B";
        }
        String out = "";
        long b = bytes;

        String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB"};
        for (String unit : units) {
            long x = b & 0b11_1111_1111;
            if (x > 0) {
                if (out.length() > 0) {
                    out = " " + out;
                }
                out = x + unit + out;
            }
            b >>= 10;
        }

        return out;
    }
}
