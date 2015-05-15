package com.bohn.ballonpop;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by bohn on 15-05-2015.
 */
public class Balloon extends GameObject {
    private int r;

    public Balloon (int x, int y) {
        super.x = x;
        super.y = y;
        this.r = 20;
    }

    public void update() {

        super.y -= 5;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawCircle(super.x - this.r, super.y - this.r, this.r, paint);
        //canvas.drawArc(super.x - this.r, super.y-r,10,10,10,23, false, paint);

    }

}
