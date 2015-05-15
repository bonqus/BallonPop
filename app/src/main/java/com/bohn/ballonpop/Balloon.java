package com.bohn.ballonpop;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;

/**
 * Created by bohn on 15-05-2015.
 */
public class Balloon extends GameObject {
    private int r = 10;
    private int color;

    public Balloon (int x, int y) {
        super.x = x;
        super.y = y;
        Random rand = new Random();
        color = Color.rgb(rand.nextInt(255),rand.nextInt(255),rand.nextInt(255));
    }

    public void update() {
        super.x = super.x+(int)((Math.sin(super.y/100))*2);
        super.y -= 5;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(getPath2D(), paint);


    }

    public Path getPath2D() {
        Path path = new Path();
        path.moveTo(x, y);
        path.cubicTo(x - 150, y - 150, x + 150, y - 150, x, y);
        return path;
    }

}
