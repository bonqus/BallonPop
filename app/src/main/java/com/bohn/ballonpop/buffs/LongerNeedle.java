package com.bohn.ballonpop.buffs;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.bohn.ballonpop.GameObject;
import com.bohn.ballonpop.GamePanel;

/**
 * Created by bohn on 20-05-2015.
 */

public class LongerNeedle extends GameObject implements Buffs {

    private int r;


    public LongerNeedle(int x, int y) {
        super.x = x;
        super.y = y;
        super.dy = 5;
        this.r = 20;

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(super.x, super.y, this.r, paint);
        canvas.drawCircle(super.x, super.y, r-2, paint);
        canvas.drawCircle(super.x, super.y, r-4, paint);
        canvas.drawCircle(super.x, super.y, r-6, paint);
        canvas.drawCircle(super.x, super.y, r-8, paint);
    }

    @Override
    public void update() {
        super.y+=5;

    }

    @Override
    public boolean shouldRemove() {
        return super.y > GamePanel.HEIGHT+this.r;
    }




}
