package com.bohn.ballonpop.buffs;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.bohn.ballonpop.GameObject;
import com.bohn.ballonpop.GamePanel;

/**
 * Created by bohn on 21-05-2015.
 */
public abstract class Buff extends GameObject {
    protected int r = 40;
    protected Paint paint;
    protected double dy = 5;
    protected boolean remove = false;

    public void update () {
        super.y += dy+dy*0.06;
    }
    public void draw(Canvas canvas) {

    }

    public int getR() {
        return r;
    }

    public boolean shouldRemove() {
        if (remove) {
            return true;
        }
        return super.y > GamePanel.HEIGHT + this.r;
    }

    public void activate() {

    }

    public Path getPath() {
        Path path = new Path();
        path.addCircle(this.x, this.y,this.r,Path.Direction.CW);
        return path;
    }

    public void remove() {
        remove = true;
    }
}
