package com.bohn.ballonpop.smartClasses;

/**
 * Created by bohn on 25-05-2015.
 */
public class PositionSize {
    int x;
    int y;
    int r;
    public PositionSize(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.r = radius;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getR() {
        return this.r;
    }
}
