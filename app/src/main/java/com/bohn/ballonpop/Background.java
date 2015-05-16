package com.bohn.ballonpop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by bohn on 15-05-2015.
 */
public class Background {
    private int w;
    private int h;
    public Background(int w, int h) {
        this.w = w;
        this.h = h;
    }

    public void update() {

    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(199,233,245));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawRect(0,0,this.w,this.h, paint);

    }
}

