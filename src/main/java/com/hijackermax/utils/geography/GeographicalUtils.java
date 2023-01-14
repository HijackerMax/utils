package com.hijackermax.utils.geography;

import com.hijackermax.utils.models.MercatorCoordinates;
import com.hijackermax.utils.models.WSG84Coordinates;

/**
 * Set of utility methods that can help to work with coordinates
 */
public final class GeographicalUtils {
    private static final double MERCATOR_BOUNDARY = 20037508.342789244;

    private GeographicalUtils() {
    }

    /**
     * Returns calculated WSG84 coordinates model {@link WSG84Coordinates} from Pseudo-Mercator coordinates
     *
     * @param x east cartesian coordinates of point, meters
     * @param y north cartesian coordinates of point, meters
     * @return {@link WSG84Coordinates} WSG84 coordinates of point, degrees
     * @since 0.0.1
     */
    public static WSG84Coordinates mercatorToWSG84(double x, double y) {
        double longitude = (x / MERCATOR_BOUNDARY) * 180;
        double latitude = (y / MERCATOR_BOUNDARY) * 180;

        latitude = Math.toDegrees(2 * Math.atan(Math.exp(latitude * Math.PI / 180)) - Math.PI / 2);

        return new WSG84Coordinates(longitude, latitude);
    }

    /**
     * Returns calculated Pseudo-Mercator coordinates model {@link MercatorCoordinates} from WSG84 coordinates
     *
     * @param longitude of point, degrees
     * @param latitude  of point, degrees
     * @return {@link MercatorCoordinates} Pseudo-Mercator coordinates of point, meters
     * @since 0.0.1
     */
    public static MercatorCoordinates wsg84toMercator(double longitude, double latitude) {
        double x = longitude * MERCATOR_BOUNDARY / 180;
        double y = Math.log(Math.tan((90 + latitude) * Math.PI / 360)) / (Math.PI / 180);
        return new MercatorCoordinates(x, y * MERCATOR_BOUNDARY / 180);
    }
}
