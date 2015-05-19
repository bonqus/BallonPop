package com.bohn.ballonpop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bohn on 15-05-2015.
 */
public class Needle extends GameObject {

    private Paint paint;
    private double angle;
    private double revangle, moveangle;
    public int quadrant;
    private int radius;
    private int newradius;
    private int cx, cy;
    private double x, y;
    private double lx, ly;
    private float prevx=0, prevy=0;

    public Needle() {
        angle = Math.PI/2;
        radius = 100;
        cx = GamePanel.WIDTH / 2;
        cy = GamePanel.HEIGHT / 2;
        x = cx + radius * Math.cos(angle);
        y = cy + radius * Math.sin(angle);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    public void draw(Canvas canvas) {
        setLinePoint(300);

        canvas.drawCircle((int) x, (int) y, 25, paint);

        canvas.drawCircle(cx, cy, 5, paint);

        canvas.drawLine((int)x, (int)y,(int) lx, (int)ly, paint);
    }

    public void touchDown(float x, float y){
        revangle = getAngle(cx, cy, x, y);
        moveangle = getAngle(prevx, prevy, x, y);
        quadrant = getQuadrant(revangle, moveangle);
        newradius = (int) getRadius(x, y, cx, cy);
        if (newradius < 50){
            newradius = 50;
        }
        else if (newradius > 150){
            newradius = 150;
        }

        if ((Math.pow(this.x - x,2) + Math.pow(this.y - y,2)) < Math.pow(100, 2)) {
            if (quadrant == 1 || quadrant == 3){
                setAngle(getAngle(x, y, cx, cy));
            }
            else if (quadrant == 0){
                    setRadius(newradius);
            }
            else if (quadrant == 2){
                    setRadius(newradius);
            }

        }
        prevx = x;
        prevy = y;
    }

    public void setLinePoint(int d) {
        double d1 = Math.sqrt(Math.pow(cx-x, 2)+Math.pow(cy-y,2));
        lx = x + (d/d1)*(cx-x);
        ly = y + (d/d1)*(cy-y);
    }
    public double getAngle(float x, float y, float x1, float x2){
        double xDiff = x1 - x;
        double yDiff = x2 - y;
        return Math.toDegrees(Math.atan2(yDiff, xDiff))+180;
    }
    public void setAngle(double angle){
        this.angle = (Math.PI/180)*angle;
        x = cx + radius * Math.cos(this.angle);
        y = cy + radius * Math.sin(this.angle);
    }

    public double getRadius(float x, float y, float x1, float y2){
        return Math.sqrt(Math.pow(x-x1, 2)+Math.pow(y-y2,2));
    }

    public void setRadius(int radius){
        this.radius = radius;
        x = cx + radius * Math.cos(this.angle);
        y = cy + radius * Math.sin(this.angle);
    }

    public int getQuadrant(double a1, double a2){
        double ax = a1-40;
        double ay = a1;
        if (ax<0){ax+=360;}
        for (int i = 0; i < 4; i++){
            ay = ax;
            ax += 80;
            if (ax > 360) {
                ay -= 360;
                ax -= 360;
            }
            if (a2 > ay && a2 < ax){
                return i;
            }
        }
        return 0;
    }

    public int getLx() {
        return (int)this.lx;
    }

    public int getLy() {
        return (int)this.ly;
    }
}
