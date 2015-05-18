package com.bohn.ballonpop;

import android.graphics.Rect;

/**
 * Created by bohn on 13-05-2015.
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;

    public void setX (int x) {
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }

    public void setDx (int dx) {
        this.dx = dx;
    }

    public void setDy (int dy) {
        this.dy = dy;
    }

    public void setWidth (int width) {
        this.width = width;
    }

    public void setHeight (int height) {
        this.height = height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Rect getRectangle() {
        return new Rect(this.x - this.width,this.y - this.height, this.x + this.width, this.y);
    }
}
