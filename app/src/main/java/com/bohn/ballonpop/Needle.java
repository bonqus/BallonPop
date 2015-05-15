package com.bohn.ballonpop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by bohn on 15-05-2015.
 */
public class Needle extends GameObject {
    public Needle () {
        super.x = 540;
        super.y = 550;
    }

    public void update() {

    }

    public void draw (Canvas canvas) {
        int r = 30;
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(540,50,super.x,super.y,paint);
        canvas.drawCircle(540,50,r,paint);
    }
}
