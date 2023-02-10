package com.hijackermax.utils.models;

import java.util.Objects;

/**
 * Class representing Mercator coordinates
 */
public class MercatorCoordinates {
    private final double x;
    private final double y;

    /**
     * Creates new instance of MercatorCoordinates with provided x and y coordinate
     * @param x x - coordinate
     * @param y y - coordinate
     */
    public MercatorCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns x coordinate of point represented by this instance of MercatorCoordinates
     * @return x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Returns y coordinate of point represented by this instance of MercatorCoordinates
     * @return y coordinate
     */
    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MercatorCoordinates)) {
            return false;
        }
        MercatorCoordinates that = (MercatorCoordinates) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "MercatorCoordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
