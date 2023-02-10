package com.hijackermax.utils.geography;

import com.hijackermax.utils.models.MercatorCoordinates;
import com.hijackermax.utils.models.WSG84Coordinates;

/**
 * Set of utility methods that can help to work with coordinates
 */
public final class GeographicalUtils {
    private static final double EARTH_RADIUS = 6371e3;
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

    /**
     * Calculates distance between two {@link WSG84Coordinates} points using Haversine formula
     *
     * @param pointOne first {@link WSG84Coordinates} point
     * @param pointTwo second {@link WSG84Coordinates} point
     * @return distance between two provided points
     * @since 0.0.2
     */
    public static double wsg84Distance(WSG84Coordinates pointOne, WSG84Coordinates pointTwo) {
        double radLatitudeX = Math.toRadians(pointOne.getLatitude());
        double radLatitudeY = Math.toRadians(pointTwo.getLatitude());

        double longitudeDiff =  Math.toRadians(pointTwo.getLongitude()) - Math.toRadians(pointOne.getLongitude());
        double latitudeDiff = radLatitudeY - radLatitudeX;
        double a = Math.pow(Math.sin(latitudeDiff / 2), 2)
                + Math.cos(radLatitudeX) * Math.cos(radLatitudeY) * Math.pow(Math.sin(longitudeDiff / 2), 2);
        return 2 * Math.asin(Math.sqrt(a)) * EARTH_RADIUS;
    }
}
