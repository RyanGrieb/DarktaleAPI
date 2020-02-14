package com.darktale.darktaleapi.data.world;

/**
 *
 * @author Ryan
 */
public class APILocation {

    double x, y, z;

    public APILocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public APILocation(String x, String y, String z) {
        this.x = Integer.getInteger(x);
        this.y = Integer.getInteger(y);
        this.z = Integer.getInteger(z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public String toString() {
        return x + ", " + y + ", " + z;
    }
}
