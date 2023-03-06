package com.hijackermax.utils.lang;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * Set of utility methods that can help to work with dates
 */
public final class DateUtils {
    private DateUtils() {
    }

    /**
     * Provides null-safe comparison of two provided {@link Date} to determine if they same day
     *
     * @param left  first date
     * @param right second date
     * @return true if both dates are same day, false if not or one of the dates is null
     * @since 0.0.4
     */
    public static boolean sameDay(Date left, Date right) {
        if (Objects.isNull(left) || Objects.isNull(right)) {
            return false;
        }
        Instant leftInstant = left.toInstant().truncatedTo(ChronoUnit.DAYS);
        Instant rightInstant = right.toInstant().truncatedTo(ChronoUnit.DAYS);
        return leftInstant.equals(rightInstant);
    }

    /**
     * Resets provided {@link Date} to midnight time in system TZ
     *
     * @param source source date
     * @return truncated date with midnight time in system TZ, or null if source is null
     * @since 0.0.4
     */
    public static Date truncateToMidnight(Date source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return Date.from(
                source.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .truncatedTo(ChronoUnit.DAYS)
                        .toInstant()
        );
    }

    /**
     * Shifts provided {@link Date} with provided amount of ms
     *
     * @param source     source date
     * @param shiftValue amount of ms that should be used to shift source date
     * @return shifted date, or null if source is null
     * @since 0.0.4
     */
    public static Date shiftDateByMs(Date source, long shiftValue) {
        if (Objects.isNull(source)) {
            return null;
        }
        return Date.from(source.toInstant().plus(shiftValue, ChronoUnit.MILLIS));
    }

    /**
     * Shifts provided {@link Date} with provided amount of minutes
     *
     * @param source     source date
     * @param shiftValue amount of minutes that should be used to shift source date
     * @return shifted date, or null if source is null
     * @since 0.0.4
     */
    public static Date shiftDateByMinutes(Date source, int shiftValue) {
        if (Objects.isNull(source)) {
            return null;
        }
        return Date.from(source.toInstant().plus(shiftValue, ChronoUnit.MINUTES));
    }

    /**
     * Shifts provided {@link Date} with provided amount of hours
     *
     * @param source     source date
     * @param shiftValue amount of hours that should be used to shift source date
     * @return shifted date, or null if source is null
     * @since 0.0.4
     */
    public static Date shiftDateByHours(Date source, int shiftValue) {
        if (Objects.isNull(source)) {
            return null;
        }
        return Date.from(source.toInstant().plus(shiftValue, ChronoUnit.HOURS));
    }

    /**
     * Shifts provided {@link Date} with provided amount of days
     *
     * @param source     source date
     * @param shiftValue amount of days that should be used to shift source date
     * @return shifted date, or null if source is null
     * @since 0.0.4
     */
    public static Date shiftDateByDays(Date source, int shiftValue) {
        if (Objects.isNull(source)) {
            return null;
        }
        return Date.from(source.toInstant().plus(shiftValue, ChronoUnit.DAYS));
    }

    /**
     * Provides null-safe check if provided value {@link Date} between two provided boundary {@link Date}
     *
     * @param value      date that should be checked
     * @param leftBound  start boundary date
     * @param rightBound end boundary date
     * @return true if date between provided boundaries, false if not or one of the provided dates is null
     * @since 0.0.4
     */
    public static boolean between(Date value, Date leftBound, Date rightBound) {
        if (Objects.isNull(value) || Objects.isNull(leftBound) || Objects.isNull(rightBound)) {
            return false;
        }
        return leftBound.before(value) && rightBound.after(value);
    }
}