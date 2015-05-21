package com.bohn.ballonpop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by bohn on 19-05-2015.
 */
public class StartScreen extends GameObject {

    private boolean update;
    public StartScreen() {
        super.x = GamePanel.WIDTH/2;
        super.y = GamePanel.HEIGHT/2;
        dy = 1;
        dx = 1;
        update = false;
    }


    public void draw(Canvas canvas) {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/CAVEMAN.TTF");
        Paint paint = new Paint();
        paint.setTypeface(tf);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("balloon pop", super.x, super.y, paint);
    }

    public void update() {
        if (update) {
            super.y -= dy;
            dy = (dx*dx)/50;
            dx += 1;
        }
    }

    public void startUpdate() {
        update = true;
    }
}
