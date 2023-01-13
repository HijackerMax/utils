package com.hijackermax;

import com.hijackermax.utils.geography.GeographicalUtils;
import com.hijackermax.utils.models.MercatorCoordinates;
import com.hijackermax.utils.models.WSG84Coordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeographicalUtilsTest {
    @Test
    void testMercatorToWSG84() {
        WSG84Coordinates wsg84 = GeographicalUtils.mercatorToWSG84(158231.305325, 4708356.859196);
        assertEquals(38.906985999993395, wsg84.getLatitude());
        assertEquals(1.421415999996301, wsg84.getLongitude());
    }

    @Test
    void testWSG84toMercator() {
        MercatorCoordinates mercator = GeographicalUtils.wsg84toMercator(1.421416, 38.906986);
        assertEquals(158231.30532541175, mercator.getX());
        assertEquals(4708356.859196946, mercator.getY());
    }
}
