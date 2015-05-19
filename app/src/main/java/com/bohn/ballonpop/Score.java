package com.bohn.ballonpop;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by bohn on 19-05-2015.
 */
public class Score {

    public static void DRAW(Canvas canvas) {
        Paint paint = new Paint();

        Typeface tf = Typeface.create("Raleway Thin", Typeface.NORMAL);
        paint.setTypeface(tf);

        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        canvas.drawText("Score: " + GamePanel.SCORE, GamePanel.WIDTH-250,150, paint);
    }
}
