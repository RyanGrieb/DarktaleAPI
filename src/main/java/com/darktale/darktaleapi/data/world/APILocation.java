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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }
}
