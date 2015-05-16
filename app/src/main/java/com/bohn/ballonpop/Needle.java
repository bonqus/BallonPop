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
    private int radius;
    private int cx, cy;
    private double x, y;

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
        if ((Math.pow(x - this.x,2) + Math.pow(y-this.y,2)) < Math.pow(140, 2)) {
            setAngle(getAngle(x, y));
        }
    }


    public double getAngle(float x, float y){
        double xDiff = cx - x;
        double yDiff = cy - y;
        return Math.toDegrees(Math.atan2(yDiff, xDiff))+180;
    }
    public void setAngle(double angle){
        this.angle = (Math.PI/180)*angle;
        //System.out.print("angle: " + angle + "\n");
        x = cx + radius * Math.cos(this.angle);
        y = cy + radius * Math.sin(this.angle);
        //System.out.print("X: " + x + "\n");
        //System.out.print("Y: " + y + "\n");
    }
}
