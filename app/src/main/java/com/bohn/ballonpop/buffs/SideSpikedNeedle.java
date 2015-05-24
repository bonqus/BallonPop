package com.bohn.ballonpop.buffs;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by bohn on 20-05-2015.
 */
public class SideSpikedNeedle extends Buff {
    public float h;
    public  SideSpikedNeedle(int x, int y, float h){
        super.x = x;
        super.y = y;
        this.h = h;
        super.paint = new Paint();
        super.paint.setStyle(Paint.Style.FILL);
        super.paint.setAntiAlias(true);
        super.paint.setStrokeWidth(1);
    }

    public void draw(Canvas canvas) {

    }

}
