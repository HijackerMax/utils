package com.hijackermax.utils.geography;

import com.hijackermax.utils.models.MercatorCoordinates;
import com.hijackermax.utils.models.WSG84Coordinates;

public final class GeographicalUtils {
    private GeographicalUtils() {
    }
    private static final double MERCATOR_BOUNDARY = 20037508.342789244;
    public static WSG84Coordinates mercatorToWSG84(double x, double y) {
        double longitude = (x / MERCATOR_BOUNDARY) * 180;
        double latitude = (y / MERCATOR_BOUNDARY) * 180;

        latitude = Math.toDegrees(2 * Math.atan(Math.exp(latitude * Math.PI / 180)) - Math.PI / 2);

        return new WSG84Coordinates(longitude, latitude);
    }

    public static MercatorCoordinates wsg84toMercator(double longitude, double latitude) {
        double x = longitude * MERCATOR_BOUNDARY / 180;
        double y = Math.log(Math.tan((90 + latitude) * Math.PI / 360)) / (Math.PI / 180);
        return new MercatorCoordinates(x, y * MERCATOR_BOUNDARY / 180);
    }
}
