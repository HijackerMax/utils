package com.hijackermax.utils;

import com.hijackermax.utils.geography.GeographicalUtils;
import com.hijackermax.utils.models.MercatorCoordinates;
import com.hijackermax.utils.models.WSG84Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeographicalUtilsTest {

    private final double DELTA = 1e-12;

    @Test
    void testMercatorToWSG84() {
        WSG84Coordinates wsg84 = GeographicalUtils.mercatorToWSG84(158231.305325, 4708356.859196);
        assertEquals(38.906985999993395, wsg84.getLatitude(), DELTA);
        assertEquals(1.4214159999963012, wsg84.getLongitude(), DELTA);
    }

    @Test
    void testWSG84toMercator() {
        MercatorCoordinates mercator = GeographicalUtils.wsg84toMercator(1.421416, 38.906986);
        assertEquals(158231.30532541175, mercator.getX(), DELTA);
        assertEquals(4708356.859196946, mercator.getY(), DELTA);
    }

    @Test
    void testWSG84Distance() {
        double distance = GeographicalUtils.wsg84Distance(
                new WSG84Coordinates(1.4274606756656105, 38.91167766492829),
                new WSG84Coordinates(1.2949727629945995, 38.98253411357968)
        );
        assertEquals(13905.03255420155, distance, DELTA);
    }
}
