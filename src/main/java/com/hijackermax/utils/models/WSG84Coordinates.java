package com.hijackermax.utils.models;

import java.util.Objects;

/**
 * Class representing WSG84 coordinates
 */
public class WSG84Coordinates {
    private final double longitude;
    private final double latitude;

    /**
     * Creates new instance of WSG84Coordinates with provided longitude and latitude
     *
     * @param longitude of point
     * @param latitude of point
     */
    public WSG84Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Returns longitude of point represented by this instance of WSG84Coordinates
     *
     * @return longitude value
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Returns latitude of point represented by this instance of WSG84Coordinates
     *
     * @return latitude value
     */
    public double getLatitude() {
        return latitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WSG84Coordinates)) {
            return false;
        }
        WSG84Coordinates that = (WSG84Coordinates) o;
        return Double.compare(that.longitude, longitude) == 0 && Double.compare(that.latitude, latitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(longitude, latitude);
    }

    @Override
    public String toString() {
        return "WSG84Coordinates{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
