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
    private float prevx=0, prevy=0;

    public Needle() {
        angle = Math.PI/2;
        radius = 250;
        cx = GamePanel.WIDTH / 2;
        cy = GamePanel.HEIGHT / 2;
        x = cx + radius * Math.cos(angle);
        y = cy + radius * Math.sin(angle);
        paint = new Paint();
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.GREEN);
        canvas.drawCircle((int)x, (int)y, 80, paint);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(cx, cy, 20, paint);
        paint.setColor(Color.BLACK);
        canvas.drawLine((int)x, (int)y, cx, cy, paint);
    }

    public void touchDown(float x, float y){
        revangle = getAngle(cx, cy, x, y);
        moveangle = getAngle(prevx, prevy, x, y);
        quadrant = getQuadrant(revangle, moveangle);
        newradius = (int) getRadius(x, y);

        if ((Math.pow(x - this.x,2) + Math.pow(y-this.y,2)) < Math.pow(140, 2)) {
            if (quadrant == 1 || quadrant == 3){
                setAngle(getAngle(x, y, cx, cy));
            }
            else if (quadrant == 0){
                if (newradius > 200){
                    radius = newradius;
                }
            }
            else if (quadrant == 2){
                if (newradius < 300){
                    radius = newradius;
                }
            }

        }
        prevx = x;
        prevy = y;
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

    public double getRadius(float x, float y){
        return Math.sqrt(Math.pow(x-cx, 2)+Math.pow(y-cy,2));
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
}
