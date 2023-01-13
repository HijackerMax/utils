package com.hijackermax.utils.models;

import java.util.Objects;

public class WSG84Coordinates {
    private final double longitude;
    private final double latitude;

    public WSG84Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

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
}
