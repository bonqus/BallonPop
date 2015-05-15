package com.bohn.ballonpop;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by bohn on 15-05-2015.
 */
public class Balloon extends GameObject {
    private int r;

    public Balloon (int x, int y) {
        super.x = x;
        super.y = y;
    }

    public void update() {

        super.y -= 5;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(getPath2D(), paint);
        //canvas.drawCircle(super.x - this.r, super.y - this.r, this.r, paint);
        //canvas.drawArc(super.x - this.r, super.y-r,10,10,10,23, false, paint);

    }

    public Path getPath2D() {
        Path path = new Path();
        path.moveTo(x, y);
        path.cubicTo(x - 150, y - 150, x + 150, y - 150, x, y);
        return path;
    }

}
