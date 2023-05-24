package com.hijackermax.utils.lang;

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateUtilsTest {
    @Test
    void testSameDay() {
        assertFalse(DateUtils.sameDay(null, new Date()));
        assertFalse(DateUtils.sameDay(new Date(), null));
        long timeMillis = 1679970309662L; //Tue Mar 28 02:25:09 GMT 2023
        long oneDayMS = 24 * 60 * 60 * 1000;
        assertFalse(DateUtils.sameDay(new Date(timeMillis), new Date(timeMillis + oneDayMS)));
        assertFalse(DateUtils.sameDay(new Date(timeMillis + oneDayMS), new Date(timeMillis + 2 * oneDayMS)));
        int threeMinutes = 3 * 60 * 1000;
        assertTrue(DateUtils.sameDay(new Date(timeMillis), new Date(timeMillis + threeMinutes)));
        assertTrue(DateUtils.sameDay(new Date(timeMillis + threeMinutes), new Date(timeMillis + (threeMinutes / 2))));
    }

    @Test
    void testTruncateToMidnight() {
        assertNull(DateUtils.truncateToMidnight(null));
        long timeMillis = 1679970309662L; //Tue Mar 28 02:25:09 GMT 2023
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(DateUtils.truncateToMidnight(new Date(timeMillis))));
        assertEquals(0, calendar.get(Calendar.HOUR));
        assertEquals(0, calendar.get(Calendar.MINUTE));
        assertEquals(0, calendar.get(Calendar.SECOND));
        assertEquals(0, calendar.get(Calendar.MILLISECOND));
    }

    @Test
    void testShiftDayByMs() {
        assertNull(DateUtils.shiftDateByMs(null, 1000L));
        long timeMillis = 1679970309662L; //Tue Mar 28 02:25:09.662 GMT 2023
        long plusMillis = 1679970314662L; //Tue Mar 28 02:25:14.662 GMT 2023
        long minusMillis = 1679970304662L; //Tue Mar 28 02:25:04.662 GMT 2023
        assertEquals(new Date(plusMillis), DateUtils.shiftDateByMs(new Date(timeMillis), 5000L));
        assertEquals(new Date(minusMillis), DateUtils.shiftDateByMs(new Date(timeMillis), -5000L));
    }

    @Test
    void testShiftDayByMinutes() {
        assertNull(DateUtils.shiftDateByMinutes(null, 10));
        long timeMillis = 1679970309662L; //Tue Mar 28 02:25:09.662 GMT 2023
        long plusMillis = 1679970369662L; //Tue Mar 28 02:26:09.662 GMT 2023
        long minusMillis = 1679970249662L; //Tue Mar 28 02:24:09.662 GMT 2023
        assertEquals(new Date(plusMillis), DateUtils.shiftDateByMinutes(new Date(timeMillis), 1));
        assertEquals(new Date(minusMillis), DateUtils.shiftDateByMinutes(new Date(timeMillis), -1));
    }

    @Test
    void testShiftDayByHours() {
        assertNull(DateUtils.shiftDateByHours(null, 10));
        long timeMillis = 1679970309662L; //Tue Mar 28 02:25:09.662 GMT 2023
        long plusMillis = 1679973909662L; //Tue Mar 28 03:25:09.662 GMT 2023
        long minusMillis = 1679966709662L; //Tue Mar 28 01:25:09.662 GMT 2023
        assertEquals(new Date(plusMillis), DateUtils.shiftDateByHours(new Date(timeMillis), 1));
        assertEquals(new Date(minusMillis), DateUtils.shiftDateByHours(new Date(timeMillis), -1));
    }

    @Test
    void testShiftDayByDays() {
        assertNull(DateUtils.shiftDateByDays(null, 10));
        long timeMillis = 1679970309662L; //Tue Mar 28 02:25:09.662 GMT 2023
        long plusMillis = 1680056709662L; //Tue Mar 29 02:25:09.662 GMT 2023
        long minusMillis = 1679883909662L; //Tue Mar 27 02:25:09.662 GMT 2023
        assertEquals(new Date(plusMillis), DateUtils.shiftDateByDays(new Date(timeMillis), 1));
        assertEquals(new Date(minusMillis), DateUtils.shiftDateByDays(new Date(timeMillis), -1));
    }

    @Test
    void testShiftDayByMoths() {
        assertNull(DateUtils.shiftDateByDays(null, 10));
        long timeMillis = 1679970309662L; //Tue Mar 28 02:25:09.662 GMT 2023
        long plusMillis = 1682648709662L; //Fri Apr 28 29 02:25:09.662 GMT 2023
        long minusMillis = 1677551109662L; //Tue Feb 28 02:25:09.662 GMT 2023
        assertEquals(new Date(plusMillis), DateUtils.shiftDateByMonths(new Date(timeMillis), 1));
        assertEquals(new Date(minusMillis), DateUtils.shiftDateByMonths(new Date(timeMillis), -1));
    }

    @Test
    void testShiftDayByYears() {
        assertNull(DateUtils.shiftDateByDays(null, 10));
        long timeMillis = 1679970309662L; //Tue Mar 28 02:25:09.662 GMT 2023
        long plusMillis = 1711592709662L; //Thu Mar 28 02:25:09.662 GMT 2024
        long minusMillis = 1648434309662L; //Mon Mar 28 02:25:09.662 GMT 2022
        assertEquals(new Date(plusMillis), DateUtils.shiftDateByYears(new Date(timeMillis), 1));
        assertEquals(new Date(minusMillis), DateUtils.shiftDateByYears(new Date(timeMillis), -1));
    }

    @Test
    void testBetween() {
        assertFalse(DateUtils.between(null, new Date(), new Date()));
        assertFalse(DateUtils.between(new Date(), null, new Date()));
        assertFalse(DateUtils.between(new Date(), new Date(), null));
        long timeMillis = 1679970309662L; //Tue Mar 28 02:25:09.662 GMT 2023
        long plusMillis = 1680056709662L; //Tue Mar 29 02:25:09.662 GMT 2023
        long minusMillis = 1679883909662L; //Tue Mar 27 02:25:09.662 GMT 2023
        assertTrue(DateUtils.between(new Date(timeMillis), new Date(minusMillis), new Date(plusMillis)));
        assertFalse(DateUtils.between(new Date(minusMillis), new Date(timeMillis), new Date(plusMillis)));
    }

    @Test
    void testInRange() {
        Date now = new Date();
        assertFalse(DateUtils.inRange(null, now, 10));
        assertFalse(DateUtils.inRange(now, null, 10));
        assertThrows(IllegalArgumentException.class, () -> DateUtils.inRange(now, now, 0));
        long timeMillis = 1679970309662L; //Tue Mar 28 02:25:09.662 GMT 2023
        long targetMiddle = 1680143109662L; //Thu Mar 30 02:25:09.662 GMT 2023
        assertTrue(DateUtils.inRange(new Date(timeMillis), new Date(timeMillis), 86400000));
        assertFalse(DateUtils.inRange(new Date(timeMillis), new Date(targetMiddle), 86400000));
    }
}
