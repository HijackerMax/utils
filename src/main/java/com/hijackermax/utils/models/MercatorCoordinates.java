package com.hijackermax.utils.models;

import java.util.Objects;

public class MercatorCoordinates {
    private final double x;
    private final double y;

    public MercatorCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

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
}
